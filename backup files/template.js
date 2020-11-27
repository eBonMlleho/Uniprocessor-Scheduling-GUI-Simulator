var oneUnit = 40;

var LCM = 24 * oneUnit;

function setup() {
  createCanvas(1000, 700);
  
}

function draw(){
    line(0, 80, LCM, 80)
  drawCordinate();
  

  drawT1();

    
    drawT2();
  
  fill(color(110, 135, 237));
rect(oneUnit*2, oneUnit, oneUnit, oneUnit);
noFill();
  
    fill(color(110, 135, 237));
rect(oneUnit*3, oneUnit, oneUnit, oneUnit);
noFill();
  
    fill(color(110, 135, 237));
rect(oneUnit*4, oneUnit, oneUnit, oneUnit);
noFill();
  
  fill(color(200, 200, 80));
rect(oneUnit*5, oneUnit, oneUnit, oneUnit);
noFill();
  
  fill(color(200, 200, 80));
rect(oneUnit*6, oneUnit, oneUnit, oneUnit);
noFill();
  fill(color(200, 200, 80));
rect(oneUnit*7, oneUnit, oneUnit, oneUnit);
noFill();
  
    fill(color(100, 200, 80));
rect(oneUnit*8, oneUnit, oneUnit, oneUnit);
noFill();
    fill(color(100, 200, 80));
rect(oneUnit*9, oneUnit, oneUnit, oneUnit);
noFill();
  
      fill(color(78, 95, 80));
rect(oneUnit*13, oneUnit, oneUnit, oneUnit);
noFill();
      fill(color(200, 38, 6));
rect(oneUnit*19, oneUnit, oneUnit, oneUnit);
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