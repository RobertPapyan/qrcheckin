document.addEventListener("DOMContentLoaded",()=>{
    const alertBox = document.querySelector("#page-alert")

    if(!alertBox)return

    alertBox.classList.add("!right-2","!opacity-100")


    setTimeout(()=>{alertBox.classList.remove("!right-2","!opacity-100")},3000)


})