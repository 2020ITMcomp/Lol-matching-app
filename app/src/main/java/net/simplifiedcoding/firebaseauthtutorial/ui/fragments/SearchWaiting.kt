package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ViewTarget
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentHomeBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentSearchWaitingBinding



class SearchWaiting : Fragment() {


    private lateinit var db : FirebaseFirestore
    private lateinit var mUser : FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_search_waiting,container,false)
        val image:ImageView = v.findViewById(R.id.waiting)
        Glide.with(this).asGif().load(R.raw.image).into(image)
        db = FirebaseFirestore.getInstance()
        mUser = FirebaseAuth.getInstance().currentUser!!

        // TODO : Room 데이터를 가져와서 원하는 상대방과 매칭시켜주는 fragment

        if(true){ // 사실, matching이 맞아야지만, 매칭을 해주는 Algorithm을 작성해야한다.
            matchingStart()
        }




        return v

    }

    private fun matchingStart() {

        val roomRef = db.collection("rooms").get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    // TODO : 여기서 매칭 데이터에 맞는 방을 찾아야 한다.
                    if (document != null) {
                        //Log.d('d', "DocumentSnapshot data: ${document.data}")
                    } else {
                        //Log.d(TAG, "No such document")
                    }
                }

            }.addOnFailureListener { e ->
                //Log.w(TAG, "DB room reference ERROR")
            }

    }

    companion object{
        private const val TAG = "SearchWaiting Fragment TEST"
    }


}