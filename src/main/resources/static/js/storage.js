function deleteDocument(id, name) {
    let confirmed = confirm("Delete file " + name + "?");

    if (confirmed) {
        $.ajax({
            url: '/storage/' + Number(id),
            type: 'DELETE',
            data: {},
            success: function (result) {
                alert("Successfully deleted document");
            },
            error: function (result) {
                alert("Failed to delete document");
            }
        });
    }
}

let reader = new FileReader();
reader.onload = function (r_event) {
    if (String(r_event.target.result).startsWith("data:image/")) {
        document.getElementById('preview').setAttribute('src', String(r_event.target.result));
    } else {
        document.getElementById('preview').setAttribute('src', '../images/file.png');
    }
}

document.getElementById('file').addEventListener('change', function (event) {
    reader.readAsDataURL(this.files[0]);
});
