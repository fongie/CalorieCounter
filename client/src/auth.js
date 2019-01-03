export default class Auth {
    static getHeaders() {
        const user = localStorage.getItem("username");
        const pass = localStorage.getItem("password");

        let headers = new Headers();
        headers.append("Authorization", "Basic " + btoa(user+":"+pass));
        return headers;
    }
    static setCredentials(username,password) {
        localStorage.setItem("loggedin", "true");
        localStorage.setItem("username", username);
        localStorage.setItem("password", password);
    }
    static isLoggedIn() {
        if (localStorage.getItem("loggedin") === "true") {
        console.log("HILOGGEDIN");
            return true;
        } else {
        console.log("HINOTLOGGEDIN");
            return false;
        }
    }
    static unSetCredentials() {
        localStorage.setItem("loggedin", "false");
        localStorage.setItem("username", "");
        localStorage.setItem("password", "");
    }
}
