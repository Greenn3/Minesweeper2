import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.events.MouseEvent
import kotlin.random.Random

open class Field () {
    var value : Char = '0'
    val neighbors: MutableList<Field> = mutableStateListOf()
    var visible : Boolean by mutableStateOf(false)
   var flagged : Boolean by mutableStateOf(false)
}

fun main() {


    val list: MutableList<MutableList<Field?>> = mutableStateListOf(
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null),
        mutableStateListOf(null, null, null, null, null, null, null, null, null, null, null, null)

    )
    fun AddNeibors() {
        for (row in 1..10) {
            for (col in 1..10) {
                list[row][col]!!.neighbors.add(list[row - 1][col - 1]!!)
                list[row][col]!!.neighbors.add(list[row - 1][col]!!)
                list[row][col]!!.neighbors.add(list[row - 1][col + 1]!!)
                list[row][col]!!.neighbors.add(list[row][col - 1]!!)
                list[row][col]!!.neighbors.add(list[row][col + 1]!!)
                list[row][col]!!.neighbors.add(list[row + 1][col - 1]!!)
                list[row][col]!!.neighbors.add(list[row + 1][col]!!)
                list[row][col]!!.neighbors.add(list[row + 1][col + 1]!!)

            }
        }
    }



    fun GenerateMines(){
    var rowIndex: Int by mutableStateOf(0)
    var colIndex: Int by mutableStateOf(0)
    var count15: Int by mutableStateOf(0)

    while(count15 < 20) {
        rowIndex = Random.nextInt(10)
        colIndex = Random.nextInt(10)
        if (list[rowIndex + 1][colIndex + 1]!!.value == '0') {
            list[rowIndex + 1][colIndex + 1]!!.value = '*'
            count15++
        }
    }
    }
    fun SetNumbers() {

        for(row in 1..10) {
            for (col in 1..10) {

                if(list[row][col]!!.value == '*'){
                    for(n  in list[row][col]!!.neighbors){
                        if(n.value != '*'){
                            n.value++
                        }
                    }
                }

            }
        }
    }



    GenerateMines()
    AddNeibors()
    SetNumbers()






//Methods called in the table


    fun ShowNeighbors() {
        for (row in 1..10) {
            for (col in 1..10) {
                if (list[row][col]!!.value == '0' && list[row][col]!!.visible == true) {
                    for (n in list[row][col]!!.neighbors) {
                        n.visible = true
                    }
                }
            }
        }
    }

    fun End(){
        var visibleCount : Int by mutableStateOf(0)
        for (row in 1..10) {
            for (col in 1..10) {
            if(list[row][col]!!.visible){
                visibleCount++

            }
                if(visibleCount>=100){window.setTimeout({
                    window.alert("Yeaaah, You win")
                }, 300)}

            }
            }
    }


    fun GameOver(){
        window.setTimeout({
            window.confirm("Boooom! You loose")



        }, 300)



    }
   fun Reset(){
       for(row in 0..11){
           for(col in 0..11){
               list[row][col]!!.value =  '0'
               list[row][col]!!.visible = false
               list[row][col]!!.flagged = false
           }
       }

   } 

//Variables needed in the table

    var flag : Boolean by mutableStateOf(false)
    var explore : Boolean by mutableStateOf(false)
    var reset : Boolean by mutableStateOf(false)

    renderComposable(rootElementId = "root") {
        Table({
            style {
                border(5.px, LineStyle.Solid, Color.midnightblue)
                width(1137.px)
                height(100.px)
                textAlign("center")
                property("color", "pink")
            }
        }){
            Tr{
                Td({

                }){

                    Button(attrs = {
                        onClick {
                            flag = true
                            explore = false

                        }



                        style {
                            border(25.px)
                            width(100.px)
                            height(100.px)
                            fontSize(30.px)
                            if(flag){
                                backgroundColor(Color.darksalmon)
                            }
                            else{
                                backgroundColor(Color.lightsalmon)
                            }

                            margin(10.px)

                        }
                    }){
                        Text ("Flag")
                    }
                }
                Td({

                }) {
                    Button(attrs = {
                        onClick {
                            explore = true
                            flag = false
                        }
                        console.log(explore)


                        style {
                            border(25.px)
                            width(100.px)
                            height(100.px)
                            fontSize(30.px)
                            if(explore) {
                                backgroundColor(Color.darksalmon)
                            }
                            else{
                                backgroundColor(Color.lightsalmon)
                            }
                            margin(10.px)

                        }
                    }) {
                        Text("Explore")
                    }
                }
                Td({

                }) {
                    Button(attrs = {
                        onClick {

                            main()
                        }

                        style {
                            border(25.px)
                            width(100.px)
                            height(100.px)
                            fontSize(30.px)
                            textDecorationColor(Color.orange)
                            backgroundColor(Color.lightsalmon)
                            margin(10.px)

                        }
                    }) {
                        Text("Reset")
                    }
                }


            }
        }


        Table{
            for(tableRow in 1..10){
                Tr{
                    for(tableCol in 1..10){
                        Td({
                            style{
                                border(5.px, LineStyle.Solid, Color.midnightblue)
                                width(100.px)
                                height(100.px)
                                textAlign("center")
                                when(list[tableRow][tableCol]!!.value){
                                    '*'  -> property("color", "orangered")
                                    '1' -> property("color", "blue")
                                    '2' -> property("color", "green")
                                    '3' -> property("color", "red")
                                    '4' -> property("color", "purple")
                                    '5'  -> property("color",  "black")
                                    else -> property("color", "darkgray")
                                }
                                if(list[tableRow][tableCol]!!.flagged){
                                    property("color", "orangered")
                                }


                                fontSize(70.px)
                                property("table-layout", "fixed")
                                //display(FieldVisibility(list[tableRow][tableCol]!!.visible))


                            }
                            onClick {
                                if (explore) {
                                    list[tableRow][tableCol]!!.visible = true
                                    ShowNeighbors()
                                    if (list[tableRow][tableCol]!!.value == '*') {
                                        list[tableRow][tableCol]!!.visible = true
                                        GameOver()
                                    }
                                }
                                else if (flag) {
                                    if(list[tableRow][tableCol]!!.flagged && list[tableRow][tableCol]!!.visible){
                                        list[tableRow][tableCol]!!.visible = false
                                        list[tableRow][tableCol]!!.flagged = false
                                    }
                                    else {
                                        list[tableRow][tableCol]!!.flagged = true
                                        list[tableRow][tableCol]!!.visible = true
                                    }

                                }
                                End()
                            }

                        }) {
                            Span({
                                style{
                                    if(list[tableRow][tableCol]!!.visible == false){
                                        display(DisplayStyle.None)
                                    }
                                    else{
                                        display(DisplayStyle.Contents)
                                    }

                                }
                            }) {
                                if(list[tableRow][tableCol]!!.flagged){
                                    Text("☣")
                                }
                                else {
                                    Text(list[tableRow][tableCol]!!.value.toString())
                                }

                            }
                        }//td end
                    }
                }
            }
        }
    }
}












