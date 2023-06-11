function Logout (){
    sessionStorage.clear();
    window.location.assign("/customerLogin");
}

export default Logout;