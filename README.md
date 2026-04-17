# Android Portfolio

A fun Android app with games, drawing, and cool features!

## What's in the App

**Home** - About me page

**Mad Libs** - Fill in words to create funny stories

**Sci-Fi Names** - Generate random sci-fi character names

**Guess the Number** - Play a number guessing game and see your score on a leaderboard

**Pokemon** - Swipe through my favorite Pokemon and see their stats

**Doodle** - Draw on the screen with colors. Shake your phone to erase!

**Settings** - Toggle dark mode and set your player name

## What It Uses

- Java code (no Kotlin)
- Retrofit to get Pokemon data from PokeAPI
- Firebase to save high scores to the cloud
- SharedPreferences to save stuff locally
- View Binding for layouts
- The phone accelerometer for shake detection
  - Secondary: Teal (#009688)
- **XML Drawables**: 
  - Rounded corner backgrounds
  - Story display containers
  - Button styling
- **Professional Styling**: Material Design 3 components throughout

### Permissions
- INTERNET: For API calls and Firebase
- CAMERA: For Doodle camera bonus feature (ready)
- READ/WRITE_EXTERNAL_STORAGE: For saving drawings
- SENSOR: For accelerometer shake detection

## Technical Notes

### Drawing Canvas Implementation
- Uses Bitmap-based drawing for persistence
- Paint objects configured for smooth strokes
- Accelerometer data is smoothed to prevent false triggers
- Drawing cache is properly managed to prevent memory leaks

### Firebase Integration
- Error handling and user feedback
- Proper collection structure for scalability
- Server timestamps for accurate sorting

### API Integration
- Parallel Pokemon data fetching
- Proper error handling and user notifications
- Image loading with Glide caching
- Network call optimization

## Resources

- [Android Official Documentation](https://developer.android.com/)
- [Firebase Documentation](https://firebase.google.com/docs)
- [PokeAPI Documentation](https://pokeapi.co/)
- [Material Design Guidelines](https://material.io/design)