package com.example.uas.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.manager.Lifecycle
import com.example.uas.account.LoginFragment
import com.example.uas.account.RegisterFragment

// Kelas adapter untuk mengelola fragmen dalam ViewPager2 pada tampilan top navigation
class FragmentTopNavbarAdapter(
    fragmentManager: FragmentManager,
    lifecycle: androidx.lifecycle.Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    // Metode untuk mendapatkan jumlah total fragmen dalam adapter
    override fun getItemCount(): Int {
        return 2
    }

    // Metode untuk membuat dan mengembalikan fragmen sesuai dengan posisi
    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            LoginFragment()
        else
            RegisterFragment()
    }
}
