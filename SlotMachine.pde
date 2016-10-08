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
import g4p_controls.*;

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
      slider1.setValue(0.0);
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
