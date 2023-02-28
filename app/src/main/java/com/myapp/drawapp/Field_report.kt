package com.myapp.drawapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itextpdf.text.Document
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Field_report : AppCompatActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted =false
    private var isLocationPermissionGranted =false
    private var isRecordPermissionGranted =false
    private var isWritePermissionGranted =false
    private val data="data in the data o GK"

    private lateinit var newRecyclerview: RecyclerView
    private lateinit var newArraylist : ArrayList<serial_class>

    lateinit var serial_list: Array<String>
    lateinit var model_list : Array<String>
    lateinit var type_list : Array<String>
//    lateinit var sp_machine: Spinner
//    lateinit var sp_work:Spinner
    //val machinetemp = arrayOf<String>("B650","Download","CS600")
    //val worktemp = arrayOf<String>("Installation","PM","Service")
    //lateinit var temparray :ArrayList<String>
    //lateinit var name :String





    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_field_report)

        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions->

            isReadPermissionGranted=permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isLocationPermissionGranted=permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: isLocationPermissionGranted
            isRecordPermissionGranted=permissions[Manifest.permission.RECORD_AUDIO] ?: isRecordPermissionGranted
            isWritePermissionGranted=permissions[Manifest.permission.RECORD_AUDIO] ?: isWritePermissionGranted
        }
        requestPermission()
        //createOptions()

        val addbtn = findViewById<Button>(R.id.addBtn)
        val subbtn =findViewById<Button>(R.id.submit_btn_fr)
        val btnclr=findViewById<Button>(R.id.clear_btn_fr)
        val cust =findViewById<EditText>(R.id.et_customer_fr)
        val depart =findViewById<EditText>(R.id.et_department_fr)
        val desc =findViewById<EditText>(R.id.et_description_fr)
        //populate spinners
        var machinearray = arrayListOf<String>()
        try {

            val rDoc = Document()
            val rFilename = "machines.txt"
            val rFilepath = Environment.getExternalStorageDirectory()
            val rDirMain = File(rFilepath, "DrawUp")
            //rDirectory.mkdir()
            val rDirsub = File(rDirMain, rFilename)
            val lines: List<String> = rDirsub.readLines()
            Log.d("perm_test", rDirsub.toString())

            rDirsub.forEachLine {
                machinearray.add(it)
                //newArraylist.add(Field_update(it, " ", "Fail"))
                //newRecyclerview.adapter = Myadapter(newArraylist)
            }
            Log.d("checklist", "the array is $machinearray ")

        } catch (e: Exception) {
            Log.d("perm_test", e.toString())
            e.printStackTrace()
        }

        var workarray = arrayListOf<String>()
        try {

            val rDoc = Document()
            val rFilename = "work.txt"
            val rFilepath = Environment.getExternalStorageDirectory()
            val rDirMain = File(rFilepath, "DrawUp")
            //rDirectory.mkdir()
            val rDirsub = File(rDirMain, rFilename)
            val lines: List<String> = rDirsub.readLines()
            Log.d("perm_test", rDirsub.toString())

            rDirsub.forEachLine {
                workarray.add(it)
                //newArraylist.add(Field_update(it, " ", "Fail"))
                //newRecyclerview.adapter = Myadapter(newArraylist)
            }
            Log.d("workchecklist", "the array is $workarray ")

        } catch (e: Exception) {
            Log.d("workcheck", e.toString())
            e.printStackTrace()
        }


        var sp_machine=findViewById<Spinner>(R.id.sp_machine)
        var sp_work=findViewById<Spinner>(R.id.sp_work)

        val sp_adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,machinearray)
        sp_machine.adapter=sp_adapter
        val sp_adapter_work=ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,workarray)
        sp_work.adapter=sp_adapter_work
//        sp_machine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(applicationContext," this "+ machinetemp[p2],Toast.LENGTH_LONG ).show()
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//
//        }






        //val dt_pick =findViewById<TextView>(R.id.et_dtpick_fr)
        val temp2 = SimpleDateFormat("yyyy")
        val temp3 =SimpleDateFormat("MM")
        val temp4=SimpleDateFormat("dd")
        val temp7=SimpleDateFormat("HH")
        val temp5=SimpleDateFormat("mm")
        val temp6=SimpleDateFormat("ss")
        val now = Date()

        val reportnumber: String = temp2.format(now) + "" +temp3.format(now)+""+temp4.format(now)+""+temp7.format(now)+""+temp5.format(now)+""+temp6.format(now)

        val current = temp4.format(now)+"-"+temp3.format(now)+"-"+temp2.format(now)
        //dt_pick.text =current








        //var name =""
        val extras = intent.extras

           var name = extras?.getString("name")
           //val fix =extras.getString("try")
            Log.d("msgmsg",name.toString())

            //val fr_report= findViewById<TextView>(R.id.tv_reportnumber_fr)
            //fr_report.text=reportnumber

            Toast.makeText(this,name,Toast.LENGTH_LONG).show()



        //val txtcreate =PdfCreate()
        //txtcreate.test()
        //txtcreate.test2()


        newRecyclerview=findViewById(R.id.serial_recycler)
        newRecyclerview.layoutManager= LinearLayoutManager(this)

        newRecyclerview.setHasFixedSize(true)
        newArraylist= arrayListOf<serial_class>()
        val listofsn= ArrayList<String>()
        val listoftype=ArrayList<String>()
        val listofmodel=ArrayList<String>()
//val count =0
        addbtn.setOnClickListener {

            //val str_status=findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
            var str_status=sp_work.selectedItem.toString()
            val str_sn=findViewById<EditText>(R.id.et_serialnumber_fr).text.toString()
            //val str_model=findViewById<EditText>(R.id.et_model_fr).text.toString()
            val str_model=sp_machine.selectedItem.toString()
            Log.d("str",str_status)
            listoftype.add(str_status)
            listofsn.add(str_sn)
            listofmodel.add(str_model)
            Log.d("strs" ,"lets try $listoftype")

            newArraylist.add(serial_class(str_status,str_sn,str_model))
            newRecyclerview.adapter=Sn_list(newArraylist)

            //val clearstatus=findViewById<EditText>(R.id.editTextTextPersonName).text.clear()
            val clearsn=findViewById<EditText>(R.id.et_serialnumber_fr).text.clear()
            //val clearmodel=findViewById<EditText>(R.id.et_model_fr).text.clear()

        }




        subbtn.setOnClickListener {
            val cust =findViewById<EditText>(R.id.et_customer_fr).text.toString()
            val depart =findViewById<EditText>(R.id.et_department_fr).text.toString()
            val desc =findViewById<EditText>(R.id.et_description_fr).text.toString()
            val filename =cust +"_"+reportnumber+".pdf"

            //creating a Bundle package for my arraylist and adding it into one Bundle
            var bundle = Bundle()
            bundle!!.putSerializable("sn",listofsn)
            bundle!!.putSerializable("model",listofmodel)
            bundle!!.putSerializable("type",listoftype)
            val intent3 = Intent(this, MainActivity::class.java)
            //in the line bellow i put into my Intent our complete Bundle
            intent3.putExtra("bundle",bundle)
            //in the lines bellow i am passing variable by variable
            intent3.putExtra("cust",cust)
            intent3.putExtra("filename",filename)

            intent3.putExtra("depart",depart)
            intent3.putExtra("desc",desc)
            intent3.putExtra("date",current)
            intent3.putExtra("reportnumber",reportnumber)
            Log.d("username",name.toString())
            intent3.putExtra("username",name)
            startActivity(intent3)

        }
        btnclr.setOnClickListener {
            val cust =findViewById<EditText>(R.id.et_customer_fr).text.clear()
            val depart =findViewById<EditText>(R.id.et_department_fr).text.clear()
            val desc =findViewById<EditText>(R.id.et_description_fr).text.clear()
            newArraylist.clear()
            newRecyclerview.adapter?.notifyDataSetChanged()


        }


    }



    private fun requestPermission(){

        isReadPermissionGranted=ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )==PackageManager.PERMISSION_GRANTED

        isLocationPermissionGranted=ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )==PackageManager.PERMISSION_GRANTED

        isRecordPermissionGranted=ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        )==PackageManager.PERMISSION_GRANTED

        isWritePermissionGranted=ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )==PackageManager.PERMISSION_GRANTED

        val permissionRequest :MutableList<String> = ArrayList()

        if(!isReadPermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(!isLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if(!isRecordPermissionGranted){
            permissionRequest.add(Manifest.permission.RECORD_AUDIO)
        }
        if(!isWritePermissionGranted){
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(permissionRequest.isNotEmpty()){

            permissionLauncher.launch(permissionRequest.toTypedArray())
        }



    }

    private fun createOptions(){
//        try {
//
//            val rDoc = Document()
//            val rFilename = "machines.txt"
//            val rFilepath = Environment.getExternalStorageDirectory()
//            val rDirMain = File(rFilepath, "DrawUp")
//            //rDirectory.mkdir()
//            val rDirsub = File(rDirMain, rFilename)
//            val lines: List<String> = rDirsub.readLines()
//
//            rDirsub.forEachLine {
//            temparray.add(it)
//            //newArraylist.add(Field_update(it, " ", "Fail"))
//                //newRecyclerview.adapter = Myadapter(newArraylist)
//            }
//            Log.d("checklist", "the array is $temparray ")
//
//        } catch (e: Exception) {
//            Log.d("perm_test", e.toString())
//            e.printStackTrace()
//        }

    }

}