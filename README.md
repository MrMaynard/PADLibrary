**Dumped from reddit comment:**

Unfortunately, I couldn't really find anywhere that looked like a good spot for me to post it again. Fortunately, I saved the text of my post in a Word document!
It's a bit of a hassle to set up for the first time, and it's android only, but it's quite good at farming. It doesn't re-enter dungeons (I feature I planned on adding if a lot of people asked for it, but my post died before picking up too much steam), but it's pretty good at clearing them quickly. I usually just leave it running nearby while I read or do homework. If you decide to use it and have trouble following any of my instructions just let me know. Anyway, I'll paste the text from my original post here:
I'm not sure if posts like this are against the rules of this sub, so if that's the case please let me know where else I could post this.
Tired of farming low-level dungeons to rank up and gain coins/exp, I decided to throw together a "bot" of sorts to play through easy dungeons for me so I could work on homework, play other games, etc. Here's a video of the bot in action:
PAD Bot Demo 
As you can see, it requires an android device and a connection to a PC. Right now, the program simply pauses after completing a dungeon, and will start again one a new one is entered. However, I suppose I could add functionality to re-enter dungeons automatically if there's interest in that.
Anyway, here's how you can set up the bot to run on your own device:
You need the Android SDK, which can be found here. Specifically, make sure when installing various android tools that you get the tool "monkeyrunner"
On your device, you need to enable "USB Debugging." If you don't know how to do this, step one of this tutorial shows you how.
Download my Java library from here, the device-interfacing file here, and it's driver batch file here. Place all of the files in "~/adt-bundle-etc12345/sdk/tools/" and another copy of the first one (the .jar) in the "lib" folder here just to be safe.
Open up "monkey.py" in any text editor and make the following change:
sys.path.append(''' C:\Users\Eric\Desktop\ adt-bundle-windows-x86_64-20140702\sdk\ tools\lib ''')
to
sys.path.append('''C:\YOUR_DIRECTORY_PATH\NAME_OF_ADT_FOLDER\sdk\ tools\lib ''')    
Connect your android device to your PC via USB and make sure it's connected as a media device. For my phone, I can tap the notification that my phone is connected and select how it's connected. After a while, my PC will also connect it as a media device instead of as an installer.
(Optional) Get some kind of app to keep your screen on throughout the process. If your screen auto-locks, it can cause the bot to not function properly. I personally use an app called RedEye Stay Awake
Double click "monkey.bat"
After a while, the program should start running and playing PAD so long as it's in a dungeon. If you see some kind of error, try closing the command window and trying again after you ensure that your device has USB debugging enabled and is properly configured as a media device. If you have another issue, feel free to ask in the comments or shoot me a PM.
Some caveats:
This program will currently only work on 1920x1080 devices, if your device is another resolution (or the bot doesn't correctly move orbs around), leave a comment with a screenshot of your device playing pad and I'll get back to you with an updated "monkey.py" for your device.
I wrote all of this myself. I'm just an 18-year old kid with no background in graph theory or optimizations, so the paths that the program finds are not always the best. It'll sometimes only make 2 or 3 combos. I improve the algorithms in my local copy of PADLib.jar pretty regularly (or try to), so if it's been a while since this post went up shoot me a PM and I'll post an updated version.
A couple of things work differently on OS X / linux. Specifically the way file paths are handled is a little different, and you'll be using terminal/bash instead of a windows command environment. Feel free to ask more in a comment.
Lastly, and most importantly, this program was optimized for my (relatively powerful) computer. If you're running this on an older computer or a laptop, it may take a very long time to find a good path. If you want the bot to run faster, you can open up "monkey.py" and make the following change:
path = 32
to
path = X
Where X is one of the following numbers:
10: For very slow computers. Will find the best path of length 12 it can.
20: For moderate computers. Will find the best path of length 24 that it can.
If you've set the path length differently and are still dissatisfied with the bot's performance, leave a comment and I can re-work the library to provide more options for slower machines.
