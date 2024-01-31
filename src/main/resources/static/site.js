function createPost() {
    var id = document.getElementById('createId').value;
    var userId = document.getElementById('createUserId').value;
    var title = document.getElementById('createTitle').value;
    var body = document.getElementById('createBody').value;

    fetch('/createPost', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id: id, userId: userId, title: title, body: body })
    })
        .then(response => {
            if (!response.ok) {
                throw response;
            }
            return response.json();
        })
        .then(data => displayResponse(JSON.stringify(data)))
        .catch(error => {
            error.text().then(errorMessage => {
                displayResponse(errorMessage);
            });
        });
}

function getPostsByUserId() {
    var userId = document.getElementById('userId').value;

    fetch('/getPostsByUserId?userId=' + userId)
        .then(response => {
            if (!response.ok) {
                throw response;
            }
            return response.json();
        })
        .then(data => displayResponse(JSON.stringify(data)))
        .catch(error => {
            error.text().then(errorMessage => {
                displayResponse(errorMessage);
            });
        });
}


function getPostById() {
    var postId = document.getElementById('postId').value;

    fetch('/getPostById?id=' + postId)
        .then(response => {
            if (!response.ok) {
                throw response;
            }
            return response.json();
        })
        .then(data => displayResponse(JSON.stringify(data)))
        .catch(error => {
            error.text().then(errorMessage => {
                displayResponse(errorMessage);
            });
        });
}


function updatePost() {
    var id = document.getElementById('updateId').value;
    var title = document.getElementById('updateTitle').value;
    var body = document.getElementById('updateBody').value;

    fetch('/updatePostById/'+id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ title: title, body: body })
    })
        .then(response => {
            if (!response.ok) {
                throw response;
            }
            return response.json();
        })
        .then(data => displayResponse(JSON.stringify(data)))
        .catch(error => {
            error.text().then(errorMessage => {
                displayResponse(errorMessage);
            });
        });
}


function deletePost() {
    var id = document.getElementById('deletePostId').value;

    fetch('/deletePostById?id=' + id, { method: 'DELETE' })
        .then(response => {
            if (!response.ok) {
                throw response;
            }
            return response.text();
        })
        .then(message => {
            displayResponse(message);
        })
        .catch(error => {
            error.text().then(errorMessage => {
                displayResponse(errorMessage);
            });
        });
}



function displayResponse(response) {
    var tableBody = document.getElementById('responseTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';

    try {
        var data = JSON.parse(response);

        if (Array.isArray(data)) {
            data.forEach(post => {
                var row = tableBody.insertRow();
                row.insertCell(0).textContent = post.id;
                row.insertCell(1).textContent = post.userId;
                row.insertCell(2).textContent = post.title;
                row.insertCell(3).textContent = post.body;
            });
        } else {
            var row = tableBody.insertRow();
            row.insertCell(0).textContent = data.id || 'N/A';
            row.insertCell(1).textContent = data.userId || 'N/A';
            row.insertCell(2).textContent = data.title || 'N/A';
            row.insertCell(3).textContent = data.body || data;
        }
    } catch (e) {
        var row = tableBody.insertRow();
        row.insertCell(0).textContent = 'N/A';
        row.insertCell(1).textContent = 'N/A';
        row.insertCell(2).textContent = 'N/A';
        row.insertCell(3).textContent = response;
    }




}

