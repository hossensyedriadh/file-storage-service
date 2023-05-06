$(document).ready(() => {
    let reader = new FileReader();
    reader.onload = function (r_event) {
        if (String(r_event.target.result).startsWith("data:image/")) {
            document.getElementById('preview').setAttribute('src', String(r_event.target.result));
        } else {
            document.getElementById('preview').setAttribute('src', '../images/file.png');
        }
    }

    document.getElementById('multipartFile').addEventListener('change', function (event) {
        reader.readAsDataURL(this.files[0]);
    });

    let params = new URLSearchParams(window.location.search);

    let id = params.has("h") ? params.get("h") : null;

    if (id) {
        $("#" + id).addClass(" file-highlight ");
        document.getElementById(id).scrollIntoView({ behavior: "smooth", block: "end", inline: "nearest" });
    } else {
        $("#" + id).removeClass(" file-highlight ");
    }
});

function deleteDocument(name) {
    let confirmed = confirm("Delete file " + name + "?");

    if (confirmed) {
        $("#deleteSubmit").click();
    }
}
