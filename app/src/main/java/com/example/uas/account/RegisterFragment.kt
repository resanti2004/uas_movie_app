package com.example.uas.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.uas.R
import com.example.uas.admin.MainAdminActivity
import com.example.uas.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Deklarasi variabel untuk view binding, FirebaseAuth, dan SharedPreferences
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    // Metode onCreate, dipanggil saat fragment dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    // Metode onCreateView, dipanggil saat tampilan fragment dibuat
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout menggunakan view binding
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        // Inisialisasi SharedPreferences dengan nama "user_data"
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Mendapatkan referensi ke elemen UI
        val user: TextInputEditText = binding.username
        val email: TextInputEditText = binding.emailEt
        val pass: TextInputEditText = binding.passEt
        val btn_login: Button = binding.btnLogin

        // Menambahkan listener untuk tombol login
        btn_login.setOnClickListener {
            // Memeriksa apakah kolom email dan password terisi
            if (email.text.toString().isEmpty()){
                Toast.makeText(requireActivity(),"PLEASE FILL THE EMAIl",Toast.LENGTH_SHORT).show()
            }
            if (pass.text.toString().isEmpty()){
                Toast.makeText(requireActivity(),"PLEASE FILL THE PASSWORD",Toast.LENGTH_SHORT).show()
            }

            // Membuat pengguna baru menggunakan Firebase Authentication
            auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener(requireActivity()) {task ->
                    if(task.isSuccessful){
                        // Jika registrasi berhasil, menyimpan data pengguna ke SharedPreferences
                        editor.putString("username",user.text.toString())
                        editor.putString("email",email.text.toString())
                        editor.putString("password",pass.text.toString())
                        editor.apply()

                        // Mengosongkan kolom input dan menampilkan pesan sukses
                        email.text?.clear()
                        pass.text?.clear()
                        user.text?.clear()
                        Toast.makeText(requireActivity(),"Login Successfull!",Toast.LENGTH_SHORT).show()
                    } else {
                        // Jika registrasi gagal, menampilkan pesan kegagalan
                        Toast.makeText(requireActivity(),"Login Failed",Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Mengembalikan root dari view binding
        return binding.root
    }
}
