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
        .then(response => response.json())
        .then(data => displayResponse(JSON.stringify(data)))
        .catch(error => displayResponse('Error: ' + error));
}

function getPostsByUserId() {
    var userId = document.getElementById('userId').value;

    fetch('/getPostsByUserId?userId=' + userId)
        .then(response => response.json())
        .then(data => displayResponse(JSON.stringify(data)))
        .catch(error => displayResponse('Error: ' + error));
}

function getPostById() {
    var postId = document.getElementById('postId').value;

    fetch('/getPostById?id=' + postId)
        .then(response => response.json())
        .then(data => displayResponse(JSON.stringify(data)))
        .catch(error => displayResponse('Error: ' + error));
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
        .then(response => response.json())
        .then(data => displayResponse(JSON.stringify(data)))
        .catch(error => displayResponse('Error: ' + error));
}

function deletePost() {
    var id = document.getElementById('deletePostId').value;

    fetch('/deletePostById?id=' + id, { method: 'DELETE' })
        .then(response => displayResponse('Post deleted'))
        .catch(error => displayResponse('Error: ' + error));
}

function displayResponse(response) {
    var data = JSON.parse(response);

    var tableBody = document.getElementById('responseTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = ''; // Vyčistiť existujúce riadky

    data.forEach(post => {
        var row = tableBody.insertRow();
        row.insertCell(0).textContent = post.id;
        row.insertCell(1).textContent = post.userId;
        row.insertCell(2).textContent = post.title;
        row.insertCell(3).textContent = post.body;
    });
}

