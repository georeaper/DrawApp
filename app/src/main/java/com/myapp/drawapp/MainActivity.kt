package com.myapp.drawapp

import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itextpdf.text.Document
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var newRecyclerview: RecyclerView
    private lateinit var newArraylist : ArrayList<Field_update>

    lateinit var question: Array<String>
    lateinit var heading : Array<String>
    lateinit var checking : Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv=findViewById<TextView>(R.id.tv_data)


        val getinfo=intent.extras

            var custname=getinfo?.getString("cust")
            Log.d("find3",custname.toString())
            var depart=getinfo?.getString("depart")
            var desc=getinfo?.getString("desc")
            var date=getinfo?.getString("date")
            var reportnumber=getinfo?.getString("reportnumber")
            var username=getinfo?.getString("username")
            Log.d("find2",username.toString())

            val bundles:Bundle = getinfo?.get("bundle") as Bundle

                val listSn = bundles.getSerializable("sn") as ArrayList<String>
                val listModel = bundles.getSerializable("model") as ArrayList<String>
                val listType = bundles.getSerializable("type") as ArrayList<String>
                var information = "Customer :$custname\n"
                for(i in listSn.indices){
               information+= " " +listSn[i] + " "+listModel[i]+ " "+listType[i]+"\n"
                }
                //tv.setText("Customer$custname ,ReportNumber:$reportnumber ,$listType ,$listModel")
                tv.setText(information)






        val mbtn =findViewById<Button>(R.id.btnprint)
        newRecyclerview=findViewById(R.id.recycleViewMain)
        newRecyclerview.layoutManager= LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)
        newArraylist= arrayListOf<Field_update>()
        for (i in listType.indices) {
            val checkform= listType[i]+".txt"
            val dir=listModel[i]
            //in this try method i call a file from external storage and converted it in checkforms
            //it is working only inside a try catch method

            try {

                val rDoc = Document()
                val rFilename = checkform
                val rFilepath = Environment.getExternalStorageDirectory()
                val rDraw=File(rFilepath,"DrawUp")
                val rDirectory = File(rDraw, dir)
                rDirectory.mkdir()
                val rrDoc = File(rDirectory, rFilename)
                Log.d("finaloutput",rrDoc.toString())
                val lines: List<String> = rrDoc.readLines()

                rrDoc.forEachLine {
                    newArraylist.add(Field_update(it, " ", "Fail"))
                    newRecyclerview.adapter = Myadapter(newArraylist)
                }
                Log.d("checklist", "the array is $newArraylist")

            } catch (e: Exception) {
                Log.d("perm_test", e.toString())
                e.printStackTrace()
            }
        }


        //in this method below i retrieve the data from the checkform
        // and storing then into different lists
        mbtn.setOnClickListener {
            val getinfo=intent.extras

                val custname=getinfo?.getString("cust").toString()
                val depart=getinfo?.getString("depart").toString()
                val desc=getinfo?.getString("desc").toString()
                val date=getinfo?.getString("date").toString()
                val reportnumber=getinfo?.getString("reportnumber").toString()
                val username=getinfo?.getString("username").toString()
                val ifilename=getinfo?.getString("filename").toString()

                val bundles:Bundle = getinfo?.get("bundle") as Bundle

                    val listSn = bundles.getSerializable("sn") as ArrayList<String>
                    val listModel = bundles.getSerializable("model") as ArrayList<String>
                    val listType = bundles.getSerializable("type") as ArrayList<String>
                    var information = "Customer :$custname\n"
                    for(i in listSn.indices){
                        information+= " " +listSn[i] + " "+listModel[i]+ " "+listType[i]+"\n"
                    }
                    //tv.setText("Customer$custname ,ReportNumber:$reportnumber ,$listType ,$listModel")
                    tv.setText(information)




            val listq =ArrayList<String>()
            val lista =ArrayList<String>()
            val listc =ArrayList<String>()
            for (i in newArraylist.indices){
                listq.add(newArraylist[i].question)
                lista.add(newArraylist[i].heading)
                listc.add(newArraylist[i].check1)
                //newArraylist[i].heading
            }
            try{
            val filecreate =PdfCreate()
            filecreate.printPdf(ifilename,
                listq,
               lista,
               listc,
                username,
                custname,
                date,
                desc,
                depart,
                reportnumber)
            Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
            }catch(e: Exception){
                    Toast.makeText(this,"something went badly",Toast.LENGTH_LONG).show()
                Log.d("findme",e.toString())
                }




        }






    }

//    private fun getUserData() {
//        for(i in question.indices){
//
//            val news=Field_update(question[i],heading[i], checking[i])
//            newArraylist.add(news)
//
//        }
//        newRecyclerview.adapter=Myadapter(newArraylist)
//    }








}