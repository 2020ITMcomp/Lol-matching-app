# -*- coding: utf-8 -*-
"""
Created on Mon Nov 16 20:46:43 2020

@author: Seung kyu Hong
"""
import pandas as pd
import requests
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

cred = credentials.Certificate('key.json')
firebase_admin.initialize_app(cred,{
        'databaseURL' : 'https://console.firebase.google.com/project/mytestapplication-d2cb5'})

champ = pd.read_csv('C:/Users/Seung kyu Hong/MyProject/ML/Challenger/Lol_Champion.csv')
db = firestore.client()
api_key = 'RGAPI-ee102f10-919b-4a06-ad69-0ee63ad23b32'


while True:
    update = db.collection(u'update').get()
    summonerid = []
    for i in range(len(update)):
        sowhan = db.collection(u'update').document(update[0].id).get().to_dict()['nickname']
        db_dic = db.collection(u'summoner').document(sowhan)
        try:
            while db_dic.get().to_dict()==None:
                url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" +sowhan +'?api_key=' + api_key
                r = requests.get(url)
                db_dic = db.collection(u'summoner').document(sowhan)
                db_dic.set(r.json())
                season = '13'
                queue = '420'
                accountId = str(db_dic.get().to_dict()['accountId'])

                url = 'https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/' + accountId  +'?season=' + season +'&queue='+queue+ '&api_key=' + api_key
                re = requests.get(url)
                db_dic = db.collection(u'match').document(sowhan)
                db_dic.set(re.json())
    
    
            season = '13'
            queue = '420'
            accountId = str(db_dic.get().to_dict()['accountId'])

            url = 'https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/' + accountId  +'?season=' + season +'&queue='+queue+ '&api_key=' + api_key
            re = requests.get(url)
            db_dic = db.collection(u'match').document(sowhan)
            Top = {}
            Jungle = {}
            Middle = {}
            Bottom = {}
            for i in range(0,re.json()['endIndex']):
                re.json()['matches'][i]['gameId']
            print(1)
            while (re.json()['matches'][0]['gameId'] != db.collection(u'match').document(sowhan).get().to_dict()['matches'][0]['gameId']) or (len(db.collection(u'game').document(sowhan).collection('gamedata').get())==0):
                db_dic.set(re.json())
                for i in range(0,db.collection(u'match').document(sowhan).get().to_dict()['endIndex']):
                    db_dic = db.collection(u'match').document(sowhan)
                    gameId = str(db_dic.get().to_dict()['matches'][i]['gameId'])
                    while db.collection(u'game').document(sowhan).collection('gamedata').document(gameId).get().to_dict()==None:
                        url='https://kr.api.riotgames.com/lol/match/v4/matches/' + gameId + '?api_key=' + api_key
                        r = requests.get(url)
                        if r.status_code == 200: # response가 정상이면 바로 맨 밑으로 이동하여 정상적으로 코드 실행
                            pass

                        elif r.status_code == 429:
                            print('api cost full : infinite loop start')
                            print('loop location : ',i)
                            start_time = time.time()

                            while True: # 429error가 끝날 때까지 무한 루프
                                if r.status_code == 429:

                                    print('try 10 second wait time')
                                    time.sleep(10)

                                    r = requests.get(url)
                                    print(r.status_code)

                                elif r.status_code == 200: #다시 response 200이면 loop escape
                                    print('total wait time : ', time.time() - start_time)
                                    print('recovery api cost')
                                    break

                        elif r.status_code == 503: # 잠시 서비스를 이용하지 못하는 에러
                            print('service available error')
                            start_time = time.time()

                            while True:
                                if r.status_code == 503 or r.status_code == 429:

                                    print('try 10 second wait time')
                                    time.sleep(10)

                                    r = requests.get(url)
                                    print(r.status_code)

                                elif r.status_code == 200: # 똑같이 response가 정상이면 loop escape
                                    print('total error wait time : ', time.time() - start_time)
                                    print('recovery api cost')
                                    break
                        elif r.status_code == 403: # api갱신이 필요
                            print('you need api renewal')
                            print('break')
                            break
            
                        db_dic = db.collection(u'game').document(sowhan).collection(u'gamedata').document(gameId)
                        db_dic.set(r.json())
        
                
                db_dic = db.collection(u'game').document(sowhan).collection(u'gamedata')
                T = 0
                J = 0
                M = 0
                B = 0
                T_c = 0
                J_c = 0
                M_c = 0
                B_c = 0
                T_kda = 0
                T_win = 0
                J_kda = 0
                J_win = 0
                M_kda = 0
                M_win = 0
                B_kda = 0
                B_win = 0    
                Total_kda = 0
                Total_win = 0
                c=100
                Tch = {}
                Jch = {}
                mch = {}
                bch = {}
                for i in range(len(db_dic.get())):
                    idnum = 0
                    game = db_dic.document(str(db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['gameId']))
                    try:
                        for j in range(0,10):    
                            if game.get().to_dict()['participantIdentities'][j]['player']['summonerName']==sowhan:
                                idnum = j
                                break
                        ti = 0
                        if game.get().to_dict()['gameDuration'] <1500:
                            ti = 1
                        else:
                            ti = 0
        
        
                        cr = 0
                        xp = 0
                        go = 0
                        ch = 0
                        dm = 0
                        result = 0
        
                        cr = (sum(game.get().to_dict()['participants'][idnum]['timeline']['creepsPerMinDeltas'].values())/len(game.get().to_dict()['participants'][idnum]['timeline']['creepsPerMinDeltas'].values())>=7)*1
                        xp = (sum(game.get().to_dict()['participants'][idnum]['timeline']['xpPerMinDeltas'].values())/len(game.get().to_dict()['participants'][idnum]['timeline']['xpPerMinDeltas'].values())>=500)*1
                        go = (sum(game.get().to_dict()['participants'][idnum]['timeline']['goldPerMinDeltas'].values())/len(game.get().to_dict()['participants'][idnum]['timeline']['goldPerMinDeltas'].values())>280)*1
                        dm = (sum(game.get().to_dict()['participants'][idnum]['timeline']['damageTakenPerMinDeltas'].values())/len(game.get().to_dict()['participants'][idnum]['timeline']['damageTakenPerMinDeltas'].values())>300)*1
                        if ((champ[champ['Key']==game.get().to_dict()['participants'][idnum]['championId']]['Character'] == 'A')*1).any()==True:
                            ch = 2
                        else:
                            ch = 0
                
                        result = cr+xp+go+ch+dm+ti
                        if result>=4:
                
                            game.collection('Feature').document('feature').set({'f':'Offensive'})
                        else:
            
                            game.collection('Feature').document('feature').set({'f':'Defensive'})
                            
                        kill = game.get().to_dict()['participants'][idnum]['stats']['kills']
                        ass = game.get().to_dict()['participants'][idnum]['stats']['assists']
                        death = game.get().to_dict()['participants'][idnum]['stats']['deaths']
                        kda = (kill+ass)/death
                        win = game.get().to_dict()['participants'][idnum]['stats']['win']
                        lane = game.get().to_dict()['participants'][idnum]['timeline']['lane']
                        Total_kda += kda
                        if win == True:
                            Total_win += 1
                
                        if (lane == 'TOP'):
                            T_c += 1
                            T_kda += kda
                            if Tch.get(db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion'])==None:
                                Tch[db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion']] = 1
                            else:
                                Tch[db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion']] += 1
                            if win == True:
                                T_win += 1
                            if game.collection('Feature').document('feature').get().to_dict()['f'] == 'Offensive':
                                T += 1
                                continue
                            elif game.collection('Feature').document('feature').get().to_dict()['f'] == 'Defensive':
                                T -= 1
                                continue
                            
                        if lane == 'JUNGLE':
                            J_c += 1
                            J_kda += kda
                            if Jch.get(db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion'])==None:
                                Jch[db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion']] = 1
                            else:
                                Jch[db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion']] += 1
                            if win == True:
                                J_win += 1
                            if game.collection('Feature').document('feature').get().to_dict()['f'] == 'Offensive':
                                J += 1
                                continue
                            elif game.collection('Feature').document('feature').get().to_dict()['f'] == 'Defensive':
                                J -= 1
                                continue 
                            
                        if lane == 'MIDDLE':
                            M_c += 1
                            M_kda += kda
                            if Mch.get(db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion'])==None:
                                Mch[db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion']] = 1
                            else:
                                Mch[db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion']] += 1
                            if win == True:
                                M_win += 1
                            if game.collection('Feature').document('feature').get().to_dict()['f'] == 'Offensive':
                                M += 1
                                continue
                            elif game.collection('Feature').document('feature').get().to_dict()['f'] == 'Defensive':
                                M -= 1
                                continue 
                            
                        if lane == 'BOTTOM':
                            B_c += 1    
                            B_kda += kda
                            if Bch.get(db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion'])==None:
                                Bch[db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion']] = 1
                            else:
                                Bch[db.collection(u'match').document(sowhan).get().to_dict()['matches'][i]['champion']] += 1
                            if win == True:
                                B_win += 1
                            if game.collection('Feature').document('feature').get().to_dict()['f'] == 'Offensive':
                                B += 1
                                continue
                            elif game.collection('Feature').document('feature').get().to_dict()['f'] == 'Defensive':
                                B -= 1
                                continue 
                
                    except:
                        c -= 1
                t = 
                for i in range(0,3):
                    
                
                db.collection(u'summoner').document(sowhan).collection('Feature').document('Total').set({'KDA':Total_kda/c,
                             'Win':Total_win/c})        
                if T>=0:
                    db.collection(u'summoner').document(sowhan).collection('Feature').document('T').set({'T':'Offensive',
                                 'KDA':T_kda/T_c,
                                 'Win':T_win/T_c},
                                 'Champion':)
        
                else:
                    db.collection(u'summoner').document(sowhan).collection('Feature').document('T').set({'T':'Defensive',
                                 'KDA':T_kda/T_c,
                                 'Win':T_win/T_c})
    
                if J>=0:
                    db.collection(u'summoner').document(sowhan).collection('Feature').document('J').set({'J':'Offensive',
                                 'KDA':J_kda/J_c,
                                 'Win':J_win/J_c})
                else:
                    db.collection(u'summoner').document(sowhan).collection('Feature').document('J').set({'J':'Defensive',
                                 'KDA':J_kda/J_c,
                                 'Win':J_win/J_c})
    
                if M>=0:
                    db.collection(u'summoner').document(sowhan).collection('Feature').document('M').set({'M':'Offensive',
                                 'KDA':M_kda/M_c,
                                 'Win':M_win/M_c})
                else:
                    db.collection(u'summoner').document(sowhan).collection('Feature').document('M').set({'M':'Defensive',
                                 'KDA':M_kda/M_c,
                                 'Win':M_win/M_c}) 
    
                if B>=0:
                    db.collection(u'summoner').document(sowhan).collection('Feature').document('B').set({'B':'Offensive',
                                 'KDA':B_kda/B_c,
                                 'Win':B_win/B_c})
                    break
                else:
                    db.collection(u'summoner').document(sowhan).collection('Feature').document('B').set({'B':'Defensive',
                                 'KDA':B_kda/B_c,
                                 'Win':B_win/B_c})
                    break
       
        except:
            pass
        
    for i in range(len(update)):
        db.collection(u'update').document(update[0].id).delete()
        
        
        
    
    