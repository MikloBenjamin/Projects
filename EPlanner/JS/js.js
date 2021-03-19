var dt = new Date();
var lastYear = dt.getFullYear();
var days = [,31,28,31,30,31,30,31,31,30,31,30,31];
var months = ['','January','February','March','April','May','June','July','August','September','October','November','December']

var daysid, dayz, inputs;
var selectedDate = {
    "year": '',
    "month": '',
    "day": ''
}

if(dt.getFullYear() % 4 == 0){
    days[2] = 29;
}

function rdy(){
    daysid = document.getElementById('days');
    selectYear = document.getElementById("yearselector");

    for(var i = 0; i < 12; i++){
        var newOption = document.createElement("option");
        newOption.value = i+1;
        newOption.innerText = dt.getFullYear() + i
        selectYear.appendChild(newOption);
    }
    
    selectMonth = document.getElementById("monthselector");
    for(var i = 0; i < 12; i++){
        var newOption = document.createElement("option");
        newOption.value = i+1;
        newOption.innerText = months[i + 1]
        if(i < dt.getMonth()){
            newOption.disabled = true;
        }
        selectMonth.appendChild(newOption);
    }
    selectMonth.selectedIndex = JSON.stringify(dt.getMonth());
    inputs = document.getElementsByTagName("input");
    daysReset();
}


function daysReset(){
    
    daysid.innerHTML = '';
    var mnth = selectMonth.value;
    
    //console.log(mnth);
    var mdays = days[parseInt(mnth)];
    //console.log(mdays);
    var today = dt.getDate();
    var fday = new Date(dt.getFullYear(), mnth - 1, 1).getDay();
    //console.log(fday);

    for(var i = 0; i < fday; i++){
        var nDay = createDay('');
        nDay.setAttribute("class", "emptyday");
        //rows[x].innerHTML += "<div class = 'day'></div>"
        daysid.appendChild(nDay);
    }

    for(var i = 1; i <= mdays; i++){
        var nDay = createDay(JSON.stringify(i));
        var ndt = new Date();
        if(i == today && (mnth == dt.getMonth() + 1) && (ndt.getFullYear() == dt.getFullYear())){
            //rows[x].innerHTML += "<div class = 'day' id = 'today'>" + JSON.stringify(i) + "</div>"
            nDay.setAttribute("id", "today");
        }
        else{
            if(i < today && (mnth == dt.getMonth() + 1) && (ndt.getFullYear() == dt.getFullYear())){
                nDay.classList.add("disabled");
                nDay.style.pointerEvents = "none";
                nDay.style.background = "white";
                nDay.style.opacity = "0.9";
            }
        }
        if((parseInt(selectedDate["day"]) == i) && (months[mnth] == selectedDate["month"]) && (selectYear.options[selectYear.selectedIndex].innerText == selectedDate["year"])){
            nDay.classList.add("selectedDay");
        }
        daysid.appendChild(nDay);
    }

    dayz = document.getElementsByClassName("day");
    selectDay()
}

function createDay(text){
    var newDay = document.createElement("div");
    newDay.setAttribute("class", "day");
    newDay.innerText = text;
    return newDay;
}

function selectDay(){
    var mnth = selectMonth.value;
    document.getElementById("middlemonth").innerText = months[mnth];
    
    for(var i = 0; i < dayz.length; i++)(function(i){
        dayz[i].addEventListener('click', function(){
            selectedDate["day"] = this.innerText;
            if(document.getElementsByClassName("selectedDay")[0]){
                var dayclass = document.getElementsByClassName("selectedDay")[0];
                dayclass.classList.remove("selectedDay");
            }
            dayz[i].classList.add("selectedDay");
            inputs[1].value = dt.getFullYear();
            inputs[2].value = months[mnth];
            inputs[3].value = dayz[i].innerText;
            document.getElementById("submit").setAttribute("value", "Plan on " + inputs[1].value + "-" + inputs[2].value + "-" + inputs[3].value);
            selectedDate["month"] = months[mnth];
            selectedDate["year"] = JSON.stringify(dt.getFullYear());
            document.getElementById("cont").style.display = "none";
            var lf = document.getElementById("submit");
            lf.style.display = "block";
        })
    })(i);
}

function yearReset(){
    //console.log(selectYear.value);
    var ndt = new Date();
    dt.setFullYear(parseInt(selectYear.options[selectYear.selectedIndex].innerText))
    var a = 0;
    selectMonth.innerHTML = '';
    for(var i = 0; i < 12; i++){
        var newOption = document.createElement("option");
        newOption.value = i+1;
        newOption.innerText = months[i + 1]
        if(i < dt.getMonth() && (ndt.getFullYear() == dt.getFullYear())){
            newOption.disabled = true;
            a++;
        }
        selectMonth.appendChild(newOption);
    }
    selectMonth.selectedIndex = JSON.stringify(a);
    //console.log(dt);
    daysReset();
}

function showTime(){
    var clk = document.getElementById("time");
    var date = new Date();
    var h = date.getHours(); // 0 - 23
    var m = date.getMinutes(); // 0 - 59
    var s = date.getSeconds(); // 0 - 59
    var session = "AM";
    
    if(h == 0){
        h = 12;
    }
    
    if(h > 12){
        h = h - 12;
        session = "PM";
    }
    
    h = (h < 10) ? "0" + h : h;
    m = (m < 10) ? "0" + m : m;
    s = (s < 10) ? "0" + s : s;
    
    var time = h + ":" + m + ":" + s + " " + session;
    clk.innerText = time;
    setTimeout(showTime, 1000);
}

showTime();
