//package net.simplifiedcoding.firebaseauthtutorial.utils
//
//import android.util.Log
//import android.view.View
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.UserProfileChangeRequest
//import kotlinx.android.synthetic.main.fragment_profile.*
//
//class FirebaseAuthUtil{
//
//    val currentUser = FirebaseAuth.getInstance().currentUser
//
//    fun userProfileUpdateWithName(name : String){
//        val updates = UserProfileChangeRequest.Builder()
//            .setDisplayName(name)
//            .build()
//
//        currentUser?.updateProfile(updates)
//            ?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d(TAG, "userProfileUpdateWithName success")
//                } else {
//                    // TODO fill
//                }
//            }
//    }
//
//    companion object{
//        private const val TAG = "FirebaseAuthUtil Test"
//    }
//}