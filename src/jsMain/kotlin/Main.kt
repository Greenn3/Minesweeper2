import androidx.compose.runtime.*
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.events.MouseEvent
import kotlin.random.Random

open class Field  {
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


    /*val list: MutableList<MutableList<Field?>> = (0..11).map { (0..11).map { null }.toMutableList() }.toMutableList()

     */
    fun createFields() {
        for (row in 0..11) {
            for (col in 0..11) {
                var field = Field()
                list[row][col] = field
            }
        }
    }


    fun generateMines(){

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


   fun setNeighbors() {
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
    createFields()
    generateMines()
setNeighbors()






fun setNumbers() {
    for (row in 1..10) {
        for (col in 1..10) {
            if (list[row][col]!!.value == '*') {
                for (n in list[row][col]!!.neighbors) {
                    if (n.value != '*') {
                        n.value++
                    }
                }
            }

        }
    }
}
    setNumbers()




    var lost : Boolean by mutableStateOf(false)

    if(lost){
        window.setTimeout({
            window.alert("Boooom! You loose")
        }, 200)


    }



    var flag : Boolean by mutableStateOf(false)
    var explore : Boolean by mutableStateOf(false)
    var mines : Int by mutableStateOf(25)


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

    //Methods used in the table
    var isLost : Boolean by mutableStateOf(false);

    fun End(){
        var visibleCount : Int by mutableStateOf(0)
        for (row in 1..10) {
            for (col in 1..10) {
            if(list[row][col]!!.visible){
                visibleCount++

            }
                if(visibleCount>=100 && lost == false){window.setTimeout({
                    window.alert("Yeaaah, You win")
                }, 300)}

            }
            }
    }


    fun GameOver(){
        window.setTimeout({
            window.confirm("Boooom! You loose")



        }, 300)
        for(row in 1..10){
            for(col in 1..10){
                list[row][col]!!.visible = true
            }
        }
        lost = true;



    }
   fun Reset(){

      createFields()
       generateMines()
       setNeighbors()
           setNumbers()
       lost = false

   }

    fun flagCount  (): Int{
        var flagCount : Int by mutableStateOf(20)

        for(row in 1..10){
            for(col in 1..10){
                if(list[row][col]!!.flagged){
                    flagCount--
                }
            }
        }
        return flagCount
    }









    renderComposable(rootElementId = "root") {
        //Button table
        Table({
            style {
                border(5.px, LineStyle.Solid, Color.midnightblue)
                width(1137.px)
                height(100.px)
                textAlign("center")
                backgroundColor(Color.cornflowerblue)
            }
        }){
            Tr{
                //Flag button
                Td({

                }){

                    Button(attrs = {
                        onClick {
                            flag = true
                            explore = false

                        }



                        style {


                            width(125.px)
                            height(100.px)
                            fontSize(30.px)
                            if(flag){
                                backgroundColor(Color.red)
                                border(5.px, LineStyle.Inset)
                            }
                            else{
                                backgroundColor(Color.maroon)
                                border(5.px, LineStyle.Outset)
                            }

                            margin(10.px)

                        }
                    }){
                        Text ("Flag")
                    }
                }
                //Explore button
                Td({

                }) {
                    Button(attrs = {
                        onClick {
                            explore = true
                            flag = false
                        }



                        style {

                            width(125.px)
                            height(100.px)
                            fontSize(30.px)

                            if(explore) {
                                backgroundColor(Color.springgreen)
                                border(5.px, LineStyle.Inset)
                            }
                            else{
                                backgroundColor(Color.darkgreen)
                                border(5.px, LineStyle.Outset)
                            }
                            margin(10.px)

                        }
                    }) {
                        Text("Explore")
                    }
                }
                //Mines counter
                Td({

                })

                {

                    Button(attrs = {



                        style {
                            border(5.px, LineStyle.Inset)
                            width(125.px)
                            height(100.px)
                            fontSize(30.px)

                           backgroundColor(Color.white)
                            margin(10.px)

                        }
                    }) {
                        Text("Mines: ${flagCount()}")
                    }
                }
                //Reset button
                Td({

                }) {
                    Button(attrs = {
                        onClick {

                            Reset()
                        }

                        style {
                            border(5.px, LineStyle.Outset)
                            width(125.px)
                            height(100.px)
                            fontSize(30.px)
                            backgroundColor(Color.lightsalmon)
                            margin(10.px)

                        }
                    }) {
                        Text("Reset")
                    }
                }


            }
        }

//Main table
        Table({
            style{
                width(1137.px)
                height(1137.px)
            }
        })

        {

            for(tableRow in 1..10){
                Tr{
                    for(tableCol in 1..10){
                        Td({
                            style{
                                border(5.px, LineStyle.Solid, Color.midnightblue)
                                width(100.px)
                                height(100.px)
                                textAlign("center")
                                backgroundColor(Color.lightslategray)
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



                            }
                            onClick {
                                if (explore) {
                                    list[tableRow][tableCol]!!.visible = true
                                    ShowNeighbors()
                                    ShowNeighbors()
                                    ShowNeighbors()
                                    ShowNeighbors()
                                    ShowNeighbors()
                                    ShowNeighbors()
                                    ShowNeighbors()
                                    ShowNeighbors()
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
                                    else if (list[tableRow][tableCol]!!.flagged == false && list[tableRow][tableCol]!!.visible == false){
                                        list[tableRow][tableCol]!!.flagged = true
                                        list[tableRow][tableCol]!!.visible = true
                                    }

                                }
                                flagCount()
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
                                    //mines--
                                }
                                else if(list[tableRow][tableCol]!!.value == '*'){
                                    Text("💥")
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














