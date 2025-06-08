package com.example.qlsinhvien_room

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qlsinhvien_room.adapter.AdapterStudent
import com.example.qlsinhvien_room.database.AppDatabase
import com.example.qlsinhvien_room.database.DataStudent
import com.example.qlsinhvien_room.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), AdapterStudent.OnItemClickListener, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var adapter: AdapterStudent
    private lateinit var recyclerViewListSv: RecyclerView
    private lateinit var dsSv: MutableList<DataStudent>
    private var hoTen = ""
    private var msSV = ""
    private var ngaySinh = ""
    private var eMail = ""
    private var address = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDatabase.getDatabase(this)
        dsSv = mutableListOf()
        getListSV()
        init()
        initAdapter()
    }

    private fun getListSV() {
        lifecycleScope.launch {
            dsSv = db.studentDao().getListStudents()
            withContext(Dispatchers.Main) {
                initAdapter()
            }
        }
    }

    private fun initAdapter() {
        adapter = AdapterStudent(dsSv, this)
        recyclerViewListSv.layoutManager = LinearLayoutManager(this)
        recyclerViewListSv.adapter = adapter
    }

    private fun init() {
        binding.addSV.setOnClickListener(this)
        binding.updateSV.setOnClickListener(this)
        binding.deleteSV.setOnClickListener(this)
        recyclerViewListSv = binding.recyclerView
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addSV -> {
                val sv = getData()
                lifecycleScope.launch(Dispatchers.IO) {
                    db.studentDao().insert(sv)
                    withContext(Dispatchers.Main) {
                        getListSV()
                        refresh()
                    }
                }
            }

            R.id.updateSV -> {
                val sv = getData()
                lifecycleScope.launch(Dispatchers.IO) {
                    val existing = db.studentDao().update(sv)
                    if (existing == 0) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                "Mã sinh viên chưa tồn tại",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            getListSV()
                            Toast.makeText(this@MainActivity, "Cập nhật thành công", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            R.id.deleteSV -> {
                val sv = getData()
                lifecycleScope.launch(Dispatchers.IO) {
                    val existing = db.studentDao().delete(sv.msSV)
                    if (existing == 0) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                "Mã sinh viên chưa tồn tại",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            getListSV()
                            Toast.makeText(this@MainActivity, "Xóa thành công", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    fun getData(): DataStudent {
        hoTen = binding.editTextTenSv.text.toString().trim()
        msSV = binding.editTextMaSo.text.toString().trim()
        ngaySinh = binding.editTextDateBirth.text.toString().trim()
        eMail = binding.editTextEmail.text.toString().trim()
        address = binding.editTextAddress.text.toString().trim()
        val sv = DataStudent(hoTen, msSV, ngaySinh, eMail, address)
        return sv
    }

    override fun onItemClick(position: Int) {
        binding.editTextTenSv.setText(dsSv[position].hoTenSv.toString())
        binding.editTextMaSo.setText(dsSv[position].msSV.toString())
        binding.editTextDateBirth.setText(dsSv[position].ngaySinh.toString())
        binding.editTextEmail.setText(dsSv[position].eMail.toString())
        binding.editTextAddress.setText(dsSv[position].address.toString())
    }

    private fun refresh() {
        binding.editTextTenSv.setText("")
        binding.editTextMaSo.setText("")
        binding.editTextDateBirth.setText("")
        binding.editTextEmail.setText("")
        binding.editTextAddress.setText("")
    }
}