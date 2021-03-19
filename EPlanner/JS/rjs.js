var input;
function rdy(){
    var sp = document.getElementById("password");
    sp.addEventListener("mouseover", function(){
        sp.type = "text";
    })
    sp.addEventListener("mouseout", function(){
        sp.type = "password";
    })
    var sp2 = document.getElementById("cpassword");
    sp2.addEventListener("mouseover", function(){
        sp2.type = "text";
    })
    sp2.addEventListener("mouseout", function(){
        sp2.type = "password";
    })
    input = document.getElementsByTagName("input");
    clearonpaste();
}

function clearonpaste(){
    for(var i = 0; i < input.length; i++)(function(){
        input[i].addEventListener("paste", function(){
            this.disabled = true;
            this.disabled = false;
        })
        input[i].addEventListener("input", function(){
            var stringinput = this.value;
            var disabledcharacters = "\\,\'/;:[]{}$%&()`^~=+*\" ";
            for(var j = 0; j < disabledcharacters.length; j++){
                if(disabledcharacters[j] == stringinput[stringinput.length - 1]){
                    stringinput = stringinput.substring(0, stringinput.length - 1);
                    this.value = stringinput;
                    break;
                }
            }
        })
     })(i);
}