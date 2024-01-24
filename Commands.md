# Commands

A list of all commands implemented by this plugin. All commands start with **/gs**. All arguments that are placed inside [] are required. The ones inside {} are optional.

## recording

The commands that start with **/gs recording** are used to start, pause, stop, rename or delete a recording or change the default recording settings.

### start {name} {time interval} {record players}

The **start** command starts a recording. Once a recording is started every update in the world is saved in the given time interval. The updates are stored in a file saved on the server. The file name depends on the name provided in the command args. If record players is set to true the position of the players is also saved in the given time interval. 

- *name* (optional; text) => The name of the recording and the file. If not specified the name will be "Recording #???".
- *time interval* (optional; in milliseconds) => The time interval how often the world changes will be saved in milliseconds. If not specified the will be the default value. If you haven't changed it the default value is 1000 milliseconds (every second).
- *record players* (optional; true or false) => Can be *<u>true</u>* or *<u>false</u>*. If true the position of all players is saved in the given time interval. IF it is false the players are ignored. If you haven't changed it the default value is **false**.

> Here are a few Examples for this command. (For this examples we assume that the default values have not been changed):
>
> - **/gs recording start** => Starts a recording with a time interval of one second that ignores players and it will be saved under the name Recording #001
> - **/gs recording start Tower_Timelaps 60000 true** => Starts a recording with a time interval of one minute that also saves the player position every minute and it will be saved under the name Tower_Timelaps

### pause {time}

The **pause** command pauses the recording. If you have specified a time the recording will be resumed automatically after the amount of seconds you specified. 

- *time* (optional; in seconds) => The amount of seconds after which the recording is resumed

> Here are a few examples for this command.
>
> - **/gs recording pause** => Pauses the recording
> - **/gs recording pause 60** => Pauses the recording for one minute

### resume

This command only works when the recording <u>is paused</u>. If this is the case it will be resumed. 

> **/gs recording resume** => resumes the recording 

### stop {name}

The **stop** command stops the recording and saves it in a file on the server. If you specify a name the file the recording will be saved in will be named like this. If you have specified a name at the start command it will be ignored.

If you have neither specified a name in the start command nor here the recording will be named Recording #XXX

- *name* (optional; text) => The name for the file the recording will be saved in

> Here is an example for this command. (For this example we will assume it is the first recording and there was no name specified in the start command)
>
> - **/gs recording stop** => Stops the recording and saves it in a file named Recording #001
> - **/gs recording stop Time_Laps** => Stops the recording and saves it in a file named Time_Laps

## playback

### start [name]

The **start** command starts the playback of a recording.

- *name* (required; text) => The name of the recording you want to play back

> **/gs playback Time_Laps** => Plays the recording named "Tme_Laps" back

### pause

The **pause** command only works if  a playback is playing right now. If this is the case the playback will be paused.

> **/gs playback pause** => pauses the currently running playback

### resume

The **resume** command only works if the playback is paused. If this is the case the playback will be resumed

> **/gs playback resume** => resumes the currently paused playback

### stop

The **stop** command only works if a playback is loaded right now. If this is the case the playback will be stop and the world will be reset to the state before the playback has been started

> **/gs playback stop** => stops the currently loaded playback and loads the current world state

