package com.myapp.drawapp

//import com.itextpdf.text.Document

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import com.itextpdf.text.*
import com.itextpdf.text.html.WebColors
import com.itextpdf.text.pdf.*
import org.json.JSONException
import org.json.JSONObject
import java.io.*
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class PdfCreate {




    fun printPdf( filename :String ,
                  dataQuestion : ArrayList<String> ,
                  dataAnswer : ArrayList<String> ,
                  dataPass : ArrayList<String> ,
                  dataname :String,
                  datacustomer :String,
                  datadate :String,
                  datadesc:String,
                  datadepart:String,
                  datarepnum:String) {
        // create a new document
        //PdfDocument document = new PdfDocument();
        var toolsArray= arrayListOf<String>()
        try {

            val rDoc = Document()
            val rFilename = "tools.txt"
            val rFilepath = Environment.getExternalStorageDirectory()
            val rDirMain = File(rFilepath, "DrawUp")
            //rDirectory.mkdir()
            val rDirsub = File(rDirMain, rFilename)
            val lines: List<String> = rDirsub.readLines()
            Log.d("perm_test", rDirsub.toString())

            rDirsub.forEachLine {
                toolsArray.add(it)
                //newArraylist.add(Field_update(it, " ", "Fail"))
                //newRecyclerview.adapter = Myadapter(newArraylist)
            }
            //rDirsub.close()
            Log.d("checklist", "the array is $toolsArray ")

        } catch (e: Exception) {
            Log.d("perm_test", e.toString())
            e.printStackTrace()
        }

        val mDoc = Document(PageSize.A4,20f, 20f, 50f, 20f)
        //val mFilename =filename

        val mFilepath =Environment.getExternalStorageDirectory()
        val letDirectories = File(mFilepath, "DrawUp")
        val pdfdir =File(letDirectories,"Reports")
        pdfdir.mkdir()
        val mfDoc = File(pdfdir, filename)
        //val img =Environment.getExternalStorageDirectory().toString() +"/Download/logo.bmp"

        try {

            PdfWriter.getInstance(mDoc,FileOutputStream(mfDoc))
            mDoc.open()

            val urName =
                BaseFont.createFont("/res/font/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)

            val headFont =Font(urName,20f,2)
            val mImg = File(letDirectories, "newlogo.png")
            val signImg = File(letDirectories, "ipografi.png")
            Log.d("imgpath", "$mImg $signImg")

            val bm =BitmapFactory. decodeFile(mImg.toString())
            //val bm = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.img)
            val stream = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream)
            var img: Image? = null
            val byteArray = stream.toByteArray()

            try {
                img = Image.getInstance(byteArray)
            } catch (e: BadElementException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }


        mDoc.add(img)
            val bm1 =BitmapFactory. decodeFile(signImg.toString())
            //val bm = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.img)
            val stream1 = ByteArrayOutputStream()
            bm1.compress(Bitmap.CompressFormat.PNG, 100, stream1)
            var img2: Image? = null
            val byteArray1 = stream1.toByteArray()

            try {
                img2 = Image.getInstance(byteArray1)
            } catch (e: BadElementException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }




            val tabletitle=PdfPTable(1)
            //tabletitle.getDefaultCell().setBorder(Rectangle.NO_BORDER)
            var titlecell=PdfPCell(Phrase("Field Service Report", Font(urName,20f)))
            titlecell.fixedHeight= 50f
            tabletitle.addCell(titlecell)
            mDoc.add(tabletitle)


            //mDoc.add(Paragraph(Phrase(,Font(urName))))
            // Draws an image as the cell's background

            // Draws an image as the cell's background
            val myColor: BaseColor = BaseColor.LIGHT_GRAY

            //table arxiko
            val tablehead=PdfPTable(4)
            tablehead.widthPercentage =80f
            //tablehead.defaultCell.fixedHeight=30f
            var headcell =PdfPCell(Phrase("Report Number", Font(urName)))
            headcell.fixedHeight=30f
            //headcell.setBackgroundColor(WebColors.getRGBColor("#E2E2E2"))
            headcell.backgroundColor=myColor
            tablehead.addCell(headcell)

            headcell=PdfPCell(Phrase(datarepnum, Font(urName)))
            headcell.fixedHeight=30f

            tablehead.addCell(headcell)

            headcell=PdfPCell(Phrase("date", Font(urName)))
            headcell.fixedHeight=30f
            headcell.backgroundColor=myColor
            tablehead.addCell(headcell)

            headcell=PdfPCell(Phrase(datadate, Font(urName)))
            headcell.fixedHeight=30f
            tablehead.addCell(headcell)

            headcell=PdfPCell(Phrase("Customer Name", Font(urName)))
            headcell.fixedHeight=30f
            tablehead.addCell(headcell)

            headcell=PdfPCell(Phrase(datacustomer, Font(urName)))
            headcell.fixedHeight=30f
            tablehead.addCell(headcell)

            headcell=PdfPCell(Phrase("Department", Font(urName)))
            headcell.fixedHeight=30f
            tablehead.addCell(headcell)

            headcell=PdfPCell(Phrase(datadepart, Font(urName)))
            headcell.fixedHeight=30f
            headcell.isUseBorderPadding
            tablehead.addCell(headcell)


            mDoc.add(tablehead)
            val p= Paragraph(20f," ")
            mDoc.add(p)

            //table of comment section
            val tabledesc =PdfPTable(1)
            tabledesc.widthPercentage=80f
            tabledesc.defaultCell.fixedHeight=80f
            val fdesc =PdfPCell(Phrase("Comments:\n \n $datadesc", Font(urName)))
            //fdesc.fixedHeight=120f

            tabledesc.addCell(fdesc)
            //tabledesc.addCell(PdfPCell(Phrase(datadesc, Font(urName))))
            mDoc.add(tabledesc)
            mDoc.add(p)
            //table of signed person

            val tablefoot = PdfPTable(2)
            val  f= PdfPCell(Phrase("This document signed by", Font(urName)))
            f.fixedHeight=30f
            tablefoot.addCell(f)
            val fname=PdfPCell(Phrase(dataname, Font(urName)))
            fname.fixedHeight=30f
            tablefoot.addCell(fname)
            val signcell=PdfPCell(Phrase("Signature", Font(urName)))
            fname.fixedHeight=50f
            tablefoot.addCell(signcell)
            //val signimg=PdfPCell(Phrase(dataname, Font(urName)))
            //fname.fixedHeight=50f
            tablefoot.addCell(img2)


            mDoc.add(tablefoot)

            mDoc.add(p)

            var tabletools =PdfPTable(1)
            tabletools.defaultCell
            for(i in toolsArray.indices){
            //tabletools.addCell(PdfPCell(Phrase(toolsArray[i]),Font(urName)))
                tabletools.addCell(PdfPCell(Phrase(toolsArray[i], Font(urName))))

                //mDoc.add(Paragraph(,)
            }
            mDoc.add(tabletools)

            mDoc.add(p)






            mDoc.newPage()
            //table of checkform
            val table= PdfPTable(3)
            table.widthPercentage=80f
            table.defaultCell.fixedHeight=50f
            table.defaultCell.border=1

            table.addCell(PdfPCell(Phrase("Inspection Title", Font(urName))))
            table.addCell(PdfPCell(Phrase("Measurement", Font(urName))))
            table.addCell(PdfPCell(Phrase("Pass/Fail", Font(urName))))


            for(i in dataQuestion.indices){
                if(dataQuestion[i]=="SerialNumber"){
                    table.addCell(PdfPCell(Phrase(" ", Font(urName))))
                    table.addCell(PdfPCell(Phrase(" ", Font(urName))))
                    table.addCell(PdfPCell(Phrase(" ", Font(urName))))

                    table.addCell(PdfPCell(Phrase(dataQuestion[i], Font(urName))))
                    table.addCell(PdfPCell(Phrase(dataAnswer[i], Font(urName))))
                    table.addCell(PdfPCell(Phrase(dataPass[i], Font(urName))))

                }else{
                table.addCell(PdfPCell(Phrase(dataQuestion[i], Font(urName))))
                table.addCell(PdfPCell(Phrase(dataAnswer[i], Font(urName))))
                table.addCell(PdfPCell(Phrase(dataPass[i], Font(urName))))
                }

            }
            mDoc.add(table)





            mDoc.close()
        }catch (e: Exception){
            Log.d("expexp","failed")

        }

    }

    fun createJson() {
        val rFilepath = Environment.getExternalStorageDirectory()
        val rDirMain = File(rFilepath, "DrawUp")
        //rDirectory.mkdir()

        val path = "settings.json"
        val rDirsub = File(rDirMain, path)
        //val filecreate = File(path)
        val json = JSONObject()

        try {
            json.put("name", "Microsoft")
            json.put("employees", 182268)
            json.put("offices", listOf("California", "Washington", "Virginia"))
        } catch (e: JSONException) {
            Log.d("jsonfault",e.printStackTrace().toString() )
            e.printStackTrace()
        }

        try {
            PrintWriter(FileWriter(rDirsub))
                .use { it.write(json.toString()) }
        } catch (e: Exception) {
            Log.d("jsonfault2",e.toString() )
            e.printStackTrace()
        }

        

    }
}