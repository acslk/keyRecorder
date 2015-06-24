# Key Recorder

This is a simple java application for recording sequence of keyboard and mouse input, as well as delay, and playing them with hotkeys.

### How to use it

To record a sequence, run RecordMain  
Create a seqence by pressing keypad 1 (NumLock on), and name the sequence  
While recording, mouse and keyboard clicks have no delay between them. To add a period of delay, press F2 to start, and press it again to finish the delay.   
Finish recording the sequence by pressing keypad 0

To assign hotkeys for the sequences, open the path.txt, and write the hotkey (in ASCII) on the same line for its sequence


To run the recorded sequences, run ExMain, and use the hotkeys mapped in path


### Requirement

Currently, this only works on Windows

### Library

[JNativeHook](https://code.google.com/p/jnativehook/) for listening for global keyboard and mouse motions


