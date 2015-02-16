# Imports the monkeyrunner and other modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
from java.lang import Math
import time
import sys




sys.path.append('''C:\Users\Eric\Desktop\ adt-bundle-windows-x86_64-20140702\sdk\ tools\lib''')






#print sys.path 

import Driver

print "imports OK, scanning for device..."



path = 32


try:
    device = MonkeyRunner.waitForConnection()
    strPorperty = device.getProperty('model')
    print "device detected, taking screenshot..."
except:
    print "a strange error has occured, please retry"
    time.sleep(60)

DOWN_AND_UP = MonkeyDevice.DOWN_AND_UP


#package = 'jp.gungho.padEN'
#activity = 'jp.gungho.padEN.AppDelegate'


def parse( r, g, b ):
    if r > 230 and g > 230 and b > 230:
        return 0


    if r > 240:
        if g > 240:
            if b> 240:
                return 0 #white possibly?
            return 4
        else:
            #print "red"
            return 1
    if ( r > 190 ):
        if ( b > 190 ):
            #print "purple"
            return 5
        else:
            #print "pink"
            return 6
    if ( b > 240 ):
        #print "blue"
        return 2
    if ( g > 180):
        #print "green"
        return 3
    return 0#can return 0 but will stop program, 7 but will never pause

def swipe( step ,x , y):
    if( step == 1):
        #print "left"
        return left(x,y);
    elif( step == 2):
        #print "up"
        return up(x,y);
    elif( step == 3):
        #print "right"
        return right(x,y);
    elif( step == 4):
        #print "down"
        return down(x,y);
    else:
        return (x,y)
    return

def left(x,y):
    for i in range(0, 9):
        if(i>0):
            x-= 22
        device.touch(x, y, MonkeyDevice.MOVE)
        #print "move ", x, y
        time.sleep(0.12/8)
    return (x,y)

#-------------------------UP:
def up(x,y):
    for i in range(0, 9):
        if (i > 0):
            y-=22
        device.touch(x, y, MonkeyDevice.MOVE)
        #print "move ", x, y
        time.sleep(0.12/8)
    return (x,y)

#-------------------------RIGHT:
def right(x,y):
    for i in range(0, 9):
        if(i>0):
            x=x+ 22
        device.touch(x, y, MonkeyDevice.MOVE)
        #print "move ", x, y
        time.sleep(0.12/8)
    return (x,y)

#-------------------------DOWN:
def down(x,y):
    for i in range(0, 9):
        if (i > 0):
            y+=22
        device.touch(x, y, MonkeyDevice.MOVE)
        #print "move ", x, y
        time.sleep(0.12/8)
    return (x,y)

def next():
    print "dungeon nav started"
    time.sleep(1)
    device.touch( 550, 1600, DOWN_AND_UP)
    time.sleep(8)
    device.touch( 550, 1600, DOWN_AND_UP)
    time.sleep(8)
    device.touch( 550, 1600, DOWN_AND_UP)
    time.sleep(2)
    device.touch( 600, 1260, DOWN_AND_UP)
    time.sleep(1.5)

    #-----------------------------------------
    # BRANCH HERE IF FULL BOX IS DETECTED:
    #-----------------------------------------

    result = device.takeSnapshot()
    colorTuple = result.getRawPixel(890, 1220)
    if tuple[1] > 240 and tuple[2] > 240 and tuple[3] > 240:
        sellMonsters()

    #-----------------------------------------

    device.touch( 500, 550, DOWN_AND_UP)
    time.sleep(1.5)
    device.touch( 500, 950, DOWN_AND_UP)
    time.sleep(1.5)
    device.touch( 500, 550, DOWN_AND_UP)
    time.sleep(1.5)
    device.touch( 500, 1080, DOWN_AND_UP)
    time.sleep(2.5)
    device.touch( 550, 1600, DOWN_AND_UP)
    time.sleep(5)

def sellMonsters():
    while (True):
        time.sleep(1)



while(True):
    try:

        driver = Driver()
        result = device.takeSnapshot()
        driver.wipe()
        pointer = 0
        jump = 177
        print "reading orbs from screenshot..."
        sx = 100
        sy = 1125
        temp = 1
        OK = True
        for i in range(0,6):
            for j in range(0,5):
                tuple = result.getRawPixel(sx+(jump * i),sy+(j*jump))
                temp = parse(tuple[1], tuple[2], tuple[3])
                if temp == 0:
                    OK = False
                driver.insertByte(pointer , temp)
                pointer = pointer + 1

        #----------------/BUILDER

        if OK:
            x = 100
            y = 1125

            print "screenshot OK, finding path..."

            #device.touch(x,y,MonkeyDevice.DOWN)

            driver.wipe()

            driver.drive(path)

            driver.parse()



            #----------------DRAG SECTION


            print "\npath found!"
            sys.stdout.write('\a')
            sys.stdout.flush()
            time.sleep(2)

            #device.touch(x,y,MonkeyDevice.UP)
            #thread.sleep(.1)

            x = driver.getX()
            y = driver.getY()


            print "outputting path to device now..." 

            #print x
            #print y

            x = 100 + (177 * x)
            y = 1125 + (177*y)



            device.touch(x,y,MonkeyDevice.UP)
            device.touch(x,y,MonkeyDevice.DOWN)

            for i in range(0,path):
                temp = driver.getStep(i)
                (x,y) = swipe(temp, x, y)

            device.touch(x,y,MonkeyDevice.UP)
            #----------------/DRAG

            print "output complete, new cycle starting soon"
        else:
            #print "attempting to re-enter dungeon..."
            #anext()
            #time.sleep(1)
            #continue
            print "read error! system will reboot"
        for timer in range(0,18):
            time.sleep(1)
            string = '%d until restart... ' % (18-timer,)
            string+='\r'
            sys.stdout.write(string)
            sys.stdout.flush()
        string = 'Restarting...'
        string += '\t\t\t\r'
        sys.stdout.write(string)
        sys.stdout.flush()

    except:
        print "A strange error occurred..."
        for timer in range(0,5):
            time.sleep(1)
            string = 'System will reboot in: %d' % (5-timer,)
            string+='\r'
            sys.stdout.write(string)
            sys.stdout.flush()
        string = 'Restarting...'
        string += '\t\t\t\r'
        sys.stdout.write(string)
        sys.stdout.flush()








