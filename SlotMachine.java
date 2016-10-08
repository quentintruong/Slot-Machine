import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import g4p_controls.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SlotMachine extends PApplet {

/** 
 * @student.info 
 *<BR> Name:          Quentin Truong
 *<BR> Date:          5-17-13
 *<BR> Period:        5
 *<BR> Assignment:    Slot Machine
 *<BR> Description:   Create a class with a gui that acts as a slot machine.
 *<BR> Cite Sources:  http://www.processing.org/learning/overview/; Reel images from Google; GUI image made in Gimp
 */

// Need G4P library


private int[] reel = new int [] {1, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 
5, 5, 5, 5,5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};//total 64 numbers.

private int[][] display = new int [][] { {1, 2, 3},
                                         {4, 5, 6},
                                         {2, 3, 4} };//col x row in processing
PImage Seven;
PImage Bar;
PImage Diamond;
PImage Watermelon;
PImage Lemon;
PImage Cherry;
PImage Background;

int myCoins = 10;

boolean spinning = false;//true = spinning, false = not spinning;
boolean reelOne = false;//true = spinning, false = done
boolean reelTwo = false;
boolean reelThree = false;
boolean justFinished = false;

public void setup()
{
  size(1350, 900, JAVA2D);
  createGUI();
  customGUI();
  // Place your setup code here
  
  Seven = loadImage("Seven.jpg");
  Bar = loadImage("Bar.jpg");
  Diamond = loadImage("Diamond.jpg");
  Watermelon = loadImage("Watermelon.jpg");
  Lemon = loadImage("Lemon.jpg");
  Cherry = loadImage("Cherry.jpg");
  
  frameRate(6);
  textSize(60);
  coinsUpdate();
  gameUpdate();
}

// Use this method to add additional statements
// to customise the GUI controls
public void customGUI()
{
  Background = loadImage("SlotMachineFinal.jpg");
  background(Background);
}

public void draw()
{
  if (spinning == true)
  {
    gameUpdate();
    if (justFinished == true)
    {
      spinning = false;
      justFinished = false;
      myCoins += calculate();
      coinsUpdate();
      slider1.setValue(0.0f);
    }
  }
}

private void coinsUpdate()
{
      noStroke();
      strokeWeight(1);
      fill(0, 0, 0);
      rect(40, 10, 100, 70);
      fill(221, 204, 61);
      text(myCoins, 40, 75);
}

public void initiate()
{
    background(Background);
    gameUpdate();
    myCoins -= 1;
    coinsUpdate();
    spinning = true;
    reelOne = true;
    reelTwo = true;
    reelThree = true;
}

public void gameUpdate()
{
  if (reelOne == false)//if reelOne is not spinning/done, then set to finished pictures
  {
    update(0);
  }
  else//else keep moving it
  {
    scrollUpdate(0);
  }
  
  if (reelTwo == false)
  {
    update(1);
  }
  else
  {
    scrollUpdate(1);
  }
  
  if (reelThree == false)
  {
    update(2);
  }
  else
  {
    scrollUpdate(2);
  }
  
  if ((reelOne || reelTwo || reelThree) == false && spinning == true)//if nothing is spinning and game is on, tell the game that it is finished
  {
    justFinished = true;
  }
}

private void update(int col)//puts pictures in according to display[][]'s contents
{
  for (int row = 0; row < 3; row++)
  {  
    placePictureDisplay(col, row);
  }
}

private void scrollUpdate(int col)//keeps each reel spinning- first one is randomly put in, 2nd = 1st, 3rd = 2nd
{
  display[col][2] = display[col][1];
  display[col][1] = display[col][0];
  display[col][0] = choosePicture();
  update(col);
}

private void placePictureDisplay(int col, int row)//places a single picture in a single spot
{
    switch (display[col][row])
    {
      case 1:
      image(Seven, (col * 376) + 198, (row * 243) + 58);
      break;
      case 2:
      image(Bar, (col * 376) + 198, (row * 243) + 58);
      break;
      case 3:
      image(Diamond, (col * 376) + 198, (row * 243) + 58);
      break;
      case 4:
      image(Watermelon, (col * 376) + 198, (row * 243) + 58);
      break;
      case 5:
      image(Lemon, (col * 376) + 198, (row * 243) + 58);
      break;
      case 6:
      image(Cherry, (col * 376) + 198, (row * 243) + 58);
      break;
    }
}

public void setPicsArray(int col)//sets one col with random pictures
{
  for (int row = 0; row < 3; row++)
  {   
      display [col][row] = choosePicture();
  }
}

private int choosePicture()//chooses a random picture by sending an int
{
    return (reel[(int) (Math.random() * reel.length)]);
}

private int calculate()
{
    int coins = 0;
    stroke(255, 0, 0);
    strokeWeight(6);
    if (display[0][1] == display[1][1] && display[1][1] == display[2][1])//goes ____
    {
        coins += worth(display[1][1]);
        line(198, 435, 1283, 435);
    }
    if (display[0][0] == display[1][1] && display[1][1] == display[2][2])//goes \\\
    {
        coins += worth(display[1][1]);
        line(198, 58, 1283, 796);
    }
    if (display[0][2] == display[1][1] && display[1][1] == display[2][0])//goes ///
    {
        coins += worth(display[1][1]);
        line(198, 796, 1283, 58);
    }
    return coins;
}

private int worth(int type)
{
  switch (type)
  {
      case 1: return 1500;
      case 2: return 750;
      case 3: return 100;
      case 4: return 40;
      case 5: return 8;
      case 6: return 4;
      default: return 0;
   }
}
/** 
 * @student.info 
 *<BR> Name:          Quentin Truong
 *<BR> Date:          5-17-13
 *<BR> Period:        5
 *<BR> Assignment:    Slot Machine
 *<BR> Description:   Create a GUI for SlotMachine
 *<BR> Cite Sources:  None
 */

/* =========================================================
 * ====                   WARNING                        ===
 * =========================================================
 * The code in this tab has been generated from the GUI form
 * designer and care should be taken when editing this file.
 * Only add/edit code inside the event handlers i.e. only
 * use lines between the matching comment tags. e.g.

 void myBtnEvents(GButton button) { //_CODE_:button1:12356:
     // It is safe to enter your event code here  
 } //_CODE_:button1:12356:
 
 * Do not rename this tab!
 * =========================================================
 */

public void slider1_change1(GSlider source, GEvent event) { //_CODE_:slider1:755963:

  if (spinning == false && myCoins > 0 && slider1.getValueF() == 1.0f)
  {
    initiate();
  }
} //_CODE_:slider1:755963:

public void button1_click1(GButton source, GEvent event) { //_CODE_:button1:605109:
  if (reelOne == true)
  {
    reelOne = false;
    setPicsArray(0);
  }
  
} //_CODE_:button1:605109:

public void button2_click1(GButton source, GEvent event) { //_CODE_:button2:607188:
  if (reelTwo == true)
  {
    reelTwo = false;
    setPicsArray(1);
  }
} //_CODE_:button2:607188:

public void button3_click1(GButton source, GEvent event) { //_CODE_:button3:214785:
  if (reelThree == true)
  {
    reelThree = false;
    setPicsArray(2);
  }
} //_CODE_:button3:214785:



// Create all the GUI controls. 
// autogenerated do not edit
public void createGUI(){
  G4P.messagesEnabled(false);
  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
  G4P.setCursor(ARROW);
  if(frame != null)
    frame.setTitle("Sketch Window");
  slider1 = new GSlider(this, 1335, 50, 750, 20, 10.0f);
  slider1.setRotation(PI/2, GControlMode.CORNER);
  slider1.setLimits(0.0f, 0.0f, 1.0f);
  slider1.setNumberFormat(G4P.DECIMAL, 1);
  slider1.setLocalColorScheme(GCScheme.GOLD_SCHEME);
  slider1.setOpaque(false);
  slider1.addEventHandler(this, "slider1_change1");
  button1 = new GButton(this, 347, 834, 50, 40);
  button1.setText("Push");
  button1.setTextBold();
  button1.setLocalColorScheme(GCScheme.GOLD_SCHEME);
  button1.addEventHandler(this, "button1_click1");
  button2 = new GButton(this, 726, 834, 50, 40);
  button2.setText("Push");
  button2.setTextBold();
  button2.setLocalColorScheme(GCScheme.GOLD_SCHEME);
  button2.addEventHandler(this, "button2_click1");
  button3 = new GButton(this, 1105, 834, 50, 40);
  button3.setText("Push");
  button3.setTextBold();
  button3.setLocalColorScheme(GCScheme.GOLD_SCHEME);
  button3.addEventHandler(this, "button3_click1");
}

// Variable declarations 
// autogenerated do not edit
GSlider slider1; 
GButton button1; 
GButton button2; 
GButton button3; 

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SlotMachine" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
