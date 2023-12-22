package com.example.uas.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.uas.admin.MainAdminActivity
import com.example.uas.databinding.FragmentLoginBinding
import com.example.uas.users.BottomNavbarActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    // Metode onCreate, dipanggil saat fragment dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Metode onCreateView, dipanggil saat tampilan fragment dibuat
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout menggunakan view binding
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        // Mendapatkan referensi ke elemen UI
        val email: TextInputEditText = binding.emailEt
        val pass: TextInputEditText = binding.passEt
        val btn_login : Button = binding.btnLogin

        // Mengecek apakah pengguna sudah login menggunakan SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val editor = sharedPreferences.edit()

        if (isLoggedIn) {
            // Pengguna sudah login, navigasikan ke layar yang sesuai
            val userType = sharedPreferences.getString("userType", "guest")
            navigateToMainMenu(userType)
        } else {
            // Menambahkan listener untuk tombol login
            btn_login.setOnClickListener {
                if (email.text.toString().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please Fill the Email!", Toast.LENGTH_SHORT).show()
                }
                if (pass.text.toString().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please Fill the Email!", Toast.LENGTH_SHORT).show()
                }

                // Melakukan proses login menggunakan Firebase Authentication
                auth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                    .addOnCompleteListener(requireActivity()) { taskk ->
                        val currentUser = auth.currentUser
                        if (taskk.isSuccessful) {
                            if (currentUser != null) {
                                // Menentukan tipe pengguna berdasarkan alamat email
                                val userType = if(currentUser.email == "admin@gmail.com"){
                                    "admin"
                                } else {
                                    "user"
                                }

                                // Menyimpan status login dan tipe pengguna ke SharedPreferences
                                editor.putBoolean("isLoggedIn", true)
                                editor.putString("userType", userType)
                                editor.apply()

                                // Navigasi ke layar yang sesuai
                                navigateToMainMenu(userType)
                            }
                        } else {
                            Toast.makeText(requireActivity(), "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        // Mengembalikan root dari view binding
        return binding.root
    }

    // Metode untuk navigasi ke layar utama sesuai dengan tipe pengguna
    private fun navigateToMainMenu(userType: String?) {
        val intentTo = when (userType) {
            "user" -> Intent(requireActivity(), BottomNavbarActivity::class.java)
            "admin" -> Intent(requireActivity(), MainAdminActivity::class.java)
            else -> null
        }
        startActivity(intentTo!!)
    }
}
