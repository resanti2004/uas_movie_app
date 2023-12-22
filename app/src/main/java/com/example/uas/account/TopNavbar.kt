// Tentukan paket dari kelas
package com.example.uas.account

// Impor pustaka Android yang diperlukan
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.uas.R
import com.example.uas.adapter.FragmentTopNavbarAdapter
import com.google.android.material.tabs.TabLayout

// Deklarasikan kelas TopNavbar, yang merupakan turunan dari AppCompatActivity
class TopNavbar : AppCompatActivity() {

    // Deklarasikan variabel untuk TabLayout, ViewPager2, dan FragmentTopNavbarAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter : FragmentTopNavbarAdapter

    // Timpa metode onCreate untuk menyiapkan aktivitas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Tetapkan tampilan konten ke tata letak yang ditentukan dalam activity_top_navbar.xml
        setContentView(R.layout.activity_top_navbar)

        // Inisialisasi TabLayout dan ViewPager2 dari tata letak
        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager2)

        // Buat instans dari FragmentTopNavbarAdapter dengan supportFragmentManager dan lifecycle
        adapter = FragmentTopNavbarAdapter(supportFragmentManager, lifecycle)

        // Tambahkan tab (Login dan Register) ke TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Login"))
        tabLayout.addTab(tabLayout.newTab().setText("Register"))

        // Tetapkan adapter untuk ViewPager2
        viewPager2.adapter = adapter

        // Tambahkan pendengar untuk menangani peristiwa pemilihan tab
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    // Tetapkan item saat ini dari ViewPager2 ke posisi tab yang dipilih
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Lakukan tidak ada ketika suatu tab tidak dipilih
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Lakukan tidak ada ketika suatu tab dipilih kembali
            }
        })

        // Daftarkan panggilan balik untuk menangani peristiwa perubahan halaman di ViewPager2
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Pilih tab yang sesuai ketika suatu halaman dipilih
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}
