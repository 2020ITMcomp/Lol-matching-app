package com.itm.lolMatching

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.itm.lolMatching.databinding.ActivityMainBinding
import com.itm.lolMatching.databinding.ItemTodoBinding

class MainActivity : AppCompatActivity() {

    // 레이아웃에 있는 친구들의 이름이 Binding이 붙어서 클래스가 생기게 된다.
    // 그 객체를 이용해서 activity에 접근해서 사용할 수 있게 된다.
    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels() // 위임 프로퍼티
    private val RC_SIGN_IN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//      setContentView(R.layout.activity_main) // R.layout에 대한 접근이 아니라 객체를 통해서 접근하도록 한다. 위의 코드

        if(FirebaseAuth.getInstance().currentUser == null){ // 로그인이 안됬을 때
            login()

        }


        // 반복되는 binding.recyclerView를 줄이기 위해서 apply를 적용시켜서 this 객체로 넘겨줌으로 편하게 사용하도록 한다.
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity) // @이것을 붙여줌으로써 this가 recyclerView가 아니게 한듯..? 레이블한정자라던데
            adapter = TodoAdapter(
                emptyList(), // 뷰모델에 넣어서 데이터를 처리하게 한다.
                onClickDeleteIcon = {
                    viewModel.deleteTodo(it) // 삭제기능을 넣은 콜백 함수를 넣은 것.
                },
                onClickItem = {
                    viewModel.toggleTodo(it)
                }
            )

        }

        binding.addButton.setOnClickListener{
            val todo = Todo(binding.editText.text.toString())
            viewModel.addTodo(todo)
        }

        // 관찰 UI 업데이트
        viewModel.todoLiveData.observe(this, Observer {
            // 라이브 데이터에 대한 값이 변경될 때 마다, List<Todo>가 넘어온다.
            (binding.recyclerView.adapter as TodoAdapter).setData(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
//                val user = FirebaseAuth.getInstance().currentUser
                viewModel.fetchData() // 데이터를 갱신해준다.
            } else {
                // 로그인 실패
                finish() // 액티비티를 끝내는 것, 원래 이런식이 아니라 다 제대로 짜야한다.
            }
        }
    }

    fun login(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }

    fun logout(){
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                login() // 로그아웃이 됬다면, 다시 로그인을 하도록 진행
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_log_out -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}





data class Todo(val text : String,
                var isDone : Boolean = false) // false를 기본값으로 주었다.

class TodoAdapter(private var myDataset: List<Todo>,
                  val onClickDeleteIcon : (todo : Todo) -> Unit,
                  val onClickItem : (todo : Todo) -> Unit) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) //viewHolder는 view를 받는 객체이므로 root를 넣어준다.
    // 모든 binding 객체는 root라는 자신이 무슨 view로부터 생성된 바인징인지를 갖고 있기 때문에 그것을 넣어주면 된다.

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TodoAdapter.TodoViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)

        return TodoViewHolder(ItemTodoBinding.bind(view))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = myDataset[position]
        holder.binding.todoText.text = todo.text

        if(todo.isDone){
            //holder.binding.todoText.paintFlags = holder.binding.todoText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            // 위의 코드가 너무 길어지니까 아래와 같이 수정한다. todotext까지가 this 객체로 넘어가기 때문에 쉽게 된다.
            holder.binding.todoText.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                setTypeface(null, Typeface.ITALIC)
            }

        }else{
            holder.binding.todoText.apply {
                paintFlags = 0
                setTypeface(null, Typeface.NORMAL)
            }
        }

        holder.binding.deleteImageView.setOnClickListener{
            onClickDeleteIcon.invoke(todo)
        }

        holder.binding.root.setOnClickListener{
            onClickItem.invoke(todo)
        }
    }

    override fun getItemCount() = myDataset.size

    fun setData(newData : List<Todo>){
        myDataset = newData
        notifyDataSetChanged()
    }
}


class MainViewModel : ViewModel(){ // 데이터를 관리하도록 만든다.
    val db = Firebase.firestore
    val todoLiveData = MutableLiveData<List<Todo>>()
    private val data = arrayListOf<Todo>()
    // 저장 자체는 data 에 넣지만, 그것을 다시 todoLiveData에 넣음으로써, 해당 livedata가 업데이트 될 때마다
    // UI적으로 업데이트를 하는 것은 MainActivity의 viewModel.todoLiveData.observe 를 통해서 하도록 한다.

    init {
        fetchData()
    }

    fun fetchData(){
        db.collection("todos")
            .get()
            .addOnSuccessListener { result ->
                data.clear()
                for (document in result) {
                    val todo = Todo(
                        document.data["text"] as String,
                        document.data["isDone"] as Boolean
                    )
                    data.add(todo)
                }
                todoLiveData.value = data
            }
    }

    fun toggleTodo(todo: Todo) {
        todo.isDone = !todo.isDone
        todoLiveData.value = data // 데이터가 변경이 될때마다 자동으로 live로 적용이 된다.
    }

    fun addTodo(todo : Todo){
        data.add(todo)
        todoLiveData.value = data
    }

    fun deleteTodo(todo: Todo){
        data.remove(todo)
        todoLiveData.value = data
    }
}