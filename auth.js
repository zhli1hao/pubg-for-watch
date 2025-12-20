import firebase from 'firebase/app';
import 'firebase/auth';

// Initialize Firebase (you need to replace the config below with your project credentials)
const firebaseConfig = {
    apiKey: "your-api-key",
    authDomain: "your-auth-domain",
    projectId: "your-project-id",
    storageBucket: "your-storage-bucket",
    messagingSenderId: "your-messaging-sender-id",
    appId: "your-app-id"
};

if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
}

const auth = firebase.auth();

// Function for guest login
auth.signInAnonymously()
    .then(() => {
        console.log('Logged in as a guest');
    })
    .catch((error) => {
        console.error('Error during guest login:', error);
    });

// Function for login with email/password
const loginWithEmail = (email, password) => {
    auth.signInWithEmailAndPassword(email, password)
        .then((userCredential) => {
            console.log('Logged in with email:', userCredential.user);
        })
        .catch((error) => {
            console.error('Error during email login:', error);
        });
};

// Function for login with mobile number
const loginWithMobile = (phoneNumber, appVerifier) => {
    auth.signInWithPhoneNumber(phoneNumber, appVerifier)
        .then((confirmationResult) => {
            // SMS sent. Ask user for the SMS code.
            const verificationCode = window.prompt('Enter the verification code you received');
            return confirmationResult.confirm(verificationCode);
        })
        .then((result) => {
            console.log('Logged in with phone number:', result.user);
        })
        .catch((error) => {
            console.error('Error during mobile login:', error);
        });
};

export { loginWithEmail, loginWithMobile };