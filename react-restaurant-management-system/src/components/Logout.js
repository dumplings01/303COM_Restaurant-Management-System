function Logout (){
    sessionStorage.clear();
    window.location.assign("/login");
}

export default Logout;