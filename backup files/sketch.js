const oneUnit = 40;
var T1_P = 8;
var T2_P = 6;
var T3_P = 24;
var LCM = 24 * oneUnit;

var draw = 2;


function setup() {
  createCanvas(1000, 700);
  
}
function draw(){
    line(0, 80, LCM, 80)
    
    
    drawCordinate();
  
    drawT1();
    
    drawT2();
  
}


function drawT1(){
fill(color(110, 135, 237));
rect(0, oneUnit, oneUnit, oneUnit);
noFill();
}

function drawT2(){
fill(color(200, 200, 80));
rect(oneUnit, oneUnit, oneUnit, oneUnit);
noFill();
}




function drawCordinate(){
let c = color(0, 0, 0,1)
stroke(c);
var i;
for (i = 0; i <= LCM; i = i + oneUnit) {
  text(i/oneUnit, i, 95);
}

line(LCM, 80, LCM, 40);

}