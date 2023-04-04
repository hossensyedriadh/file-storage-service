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
