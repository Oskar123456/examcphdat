



















const poem_list = document.querySelector("#poem_list");

function fetchPage()
{
    const url = "/poems?all=true";   
    
    fetch(url)
        .then((response) => { if (!response.ok) {
                throw new Error(`HTTP error: ${response.status}`); }
            return response.json();
        }).then((json) => {
            for (let i = 0, len = json.length; i < len; i++) {
                let e = document.createElement("li");
                let a = document.createElement("a", { href: json[i].src });
                e.innerHTML = json[i].title;
                e.appendChild(a);
                poem_list.appendChild(e);
            }
        })
        .catch((error) => {
            poem_on_display.textContent = `Could not fetch verse: ${error}`;
        });

}









