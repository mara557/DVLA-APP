# DVLA Vehicle Information App

This Android application allows users to retrieve vehicle information from the DVLA (Driver and Vehicle Licensing Agency) database using the vehicle registration number. The app sends a request to the DVLA API and displays the retrieved information to the user.

## Features
- Input field to enter the vehicle registration number.
- Submit button to initiate the API request.
- Displays vehicle information retrieved from the DVLA database.
- Error handling for invalid input and API responses.

## Usage
### Clone the Repository:
Clone this repository to your local machine using the following command:
>
bash
>git clone <repository_URL>

## API Key Setup
Obtain an API key from DVLA. This API key is required to access the DVLA API.

**Important:** To secure your API key, we've hidden the `config.properties` file from GitHub. You need to manually create this file and directory and add your API key to it. Follow these steps:

1. Create a new file named `config.properties` in the `res/raw` directory of the project.
2. Add your API key to the `config.properties` file in the following format:
   api.key=your_api_key_here
Replace `your_api_key_here` and `your_api_key_mot_here` with your actual DVLA & MOT API keys.

## Build and Run
1. Build and run the project on an Android emulator or a physical device.
2. Enter Registration Number: In the main screen of the app, enter the vehicle registration number in the provided input field.
3. Submit: Tap on the "Submit" button to initiate the request to retrieve vehicle information.
4. View Results: Once the request is processed, the app will display the retrieved vehicle information on the screen.

## Screenshots
<p>
   <img src="screenshot_1.png" alt="Screenshot" width="200" style="display:inline-block;"/>
  <img src="screenshot2.png" alt="Screenshot" width="200" style="display:inline-block; margin-right:10px;"/>
  <img src="screenshot3.png" alt="Screenshot" width="200" style="display:inline-block; margin-right:10px;"/>
  <img src="screenshot4.png" alt="Screenshot" width="200" style="display:inline-block; margin-right:10px;"/>
</p>



## Components
- **ApiKeyProvider:** Provides methods to retrieve the API keys required to access the DVLA and MOT APIs.
- **ApiRequestTask:** AsyncTask subclass responsible for making the API requests to the DVLA and MOT APIs. Executes the requests in the background and returns the responses to the main thread. Handles communication with the APIs including setting headers and parsing responses.
- **MainActivity:** Main activity of the app where users can enter the vehicle registration number. Validates input and navigates to the ResultsActivity upon submission.
- **ResultsActivity:** Displays the retrieved vehicle information from the DVLA API to the user. Handles API response, parses JSON, and dynamically creates TextViews to display information. Provides error handling for invalid responses.
- **ResultsActivityMOT:** Displays the retrieved MOT history information to the user. Handles API response, parses JSON, and dynamically creates TextViews to display information. Provides error handling for invalid responses.
### Note:
This project is currently at a sketch level and may not have a detailed or finalized architecture.

## Support and Updates
This application is a personal project developed for learning purposes and may or may not receive further updates.

**Note:** This app is for demonstration purposes only and is not affiliated with the DVLA. Use responsibly and ensure compliance with all relevant regulations and policies.
