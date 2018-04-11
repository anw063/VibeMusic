# cse-110-team-project-team-18
cse-110-team-project-team-18 

# Vibe Music

Vibe Music is an Android app that adaptively selects music to play based on your location, time of day, and other users. Like any music player, you can select tracks or albums to play.

# Vibe Music v1.0

The first iteration of Vibe Music involves a flashback mode, which automatically selects tracks to remind you of past times:  If you played a track at a place, you're more likely to hear that track when you return to that place again.  The same goes for time-of-day and day of the week.  Whether you're in flashback mode or not, the player displays the date, time, and place when the track was last played.  You can indicate a track is a favorite by clicking the + button next to the track, which will turn into a check mark and increase the chance of the track being played in flashback mode.  More recently played tracks are more likely to be played in flashback mode, but even the least-recently-played track has a chance of being played again.  If you don't want to hear a track again, clicking the + twice (or the check mark once) will indicate dislike, and an "x" will be displayed in place of the + or checkmark that was previously displayed.  Such a track will not be played in flashback mode, nor when an album containing the track is played.  Clicking the "x" returns the track's status to the neutral +.

# Vibe Music v2.0 

With Vibe Music 2.0, we introduce a replacement for Flashback Mode, called Vibe Mode (VM), which builds a playlist based on what others around you have been listening to recently.  When FBM is put into Vibe Mode, it automatically selects tracks based on what others around you have been listening to.  Priority is given to a track based on first (a) whether it was played near the user's present location, second (b) whether it was played in the last week, and third (c) whether it was played by a friend.  When multiple of these factors are present, each is given equal weight in producing the ordering of tracks.  Ties are broken according to the (a)-to-(c) ordering of the preceding criteria.

Because the track list in Vibe Mode could include any possible song, FBM now includes the ability to stream supports downloading tracks from remote sources (e.g., the same source from which another user streamed downloaded the track), rather than storing tracks.  And because Vibe Mode tracks are not stored locally, FBM remembers all the Vibe Mode tracks you've played, and All the tracks you've downloaded can be sorted according to title, album, artist, or favorite status.

As before, the player displays the date, time, and place when a track was last played.  Additionally, because that track might have been played by someone else, if the track was played by a friend, the friend's name is displayed as well, otherwise a proxy name is displayed for the anonymous user.  The proxy name can be any reasonable name (how about something cute?), but it has to be the same each time the same anonymous user is the source of the track.  If a track was played by you, then "you" is displayed in italics: you.

Finally, users stated a preference for being able to see upcoming tracks, much like shown in a traditional music player.  Thus, when playing an album or in Vibe Mode, the complete track list should be shown viewable.

