package com.lifeistech.assu.kei.original

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    private val addFragment: AddFragment = AddFragment()
    private val listFragment: ListFragment = ListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        if(savedInstanceState ==null){
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.nav_host_fragment,addFragment).commit()
        }
        nav_view.setOnNavigationItemSelectedListener{
            val transaction=supportFragmentManager.beginTransaction()
            when(it.itemId){
                R.id.AddActivity->{
                    transaction.replace(R.id.nav_host_fragment,addFragment)
                        .disallowAddToBackStack().commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.ListFragment->{
                    transaction.replace(R.id.nav_host_fragment,listFragment)
                        .disallowAddToBackStack().commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }
}
