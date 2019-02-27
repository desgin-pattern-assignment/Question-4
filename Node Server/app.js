let express = require("express");
let app = express();
let bodyParser = require("body-parser");
let fs = require("fs");
let admin = require("./data/admin.json");
let students = require("./data/students.json");
let notices = require("./data/notices.json");
const PORT = 3000;

app.use(bodyParser.urlencoded({extended: true}));

app.get("/admin", function(req, res) {
    let username = req.query["username"];
    let password = req.query["password"];
    if (!username || !password || admin[username] != password) {
        res.send("false");
    }
    res.send("true");
});

app.get("/student", function(req, res) {
    let username = req.query["username"];
    let password = req.query["password"];
    if (!username || !password || students[username] != password) {
        res.send("false");
    }
    res.send("true");
});

app.get("/register", function(req, res) {
    let username = req.query["username"];
    let password = req.query["password"];
    let isAdmin = req.query["admin"];
    if (isAdmin && admin["numberOfAdmins"] > 0) {
        res.send("1");
    } else if (admin[username] || students[username]) {
        res.send("2");
    } else if (isAdmin) {
        admin[username] = password;
        admin["numberOfAdmins"] = 1;
        fs.writeFile("./data/admin.json", JSON.stringify(admin), err => {
            if (err) {
                res.send("3");
            }
        });
    } else {
        students[username] = password;
        fs.writeFile("./data/students.json", JSON.stringify(students), err => {
            if (err) {
                res.send("4");
            }
        });
    }
    res.send("0");
});

app.get("/notice", function(req, res) {
    let id = req.query["id"];
    let detail = req.query["detail"];
    if (!id) {
        res.send("false");
    }
    if (!detail) {
        if (id < 0) {
            res.send(notices.map((item, index, array) => {
                return item["title"];
            })); // just send the titles
        } else if (id >= notices.length) {
            res.send("false");
        } else {
            res.send(notices[id]["title"]);
        }
    } else {
        if (id < 0 || id >= notices.length) {
            res.send("false");
        } else {
            res.send(notices[id]["detail"]);
        }
    }
});


app.get("/add", function(req, res) {
    let title = req.query["title"];
    let detail = req.query["detail"];
    if (notices[title]) {
        res.send("1");
    }
    notices.push({
        "title" : title,
        "detail" : detail
    });
    fs.writeFile("./data/notices.json", JSON.stringify(notices), err => {
        if (err) {
            res.send("2");
        }
    });
    res.send("0");
});

app.get("/edit", function(req, res) {
    let id = req.query["id"];
    let title = req.query["title"];
    let detail = req.query["detail"];
    notices[id] = {
        "title" : title,
        "detail" : detail
    };
    fs.writeFile("./data/notices.json", JSON.stringify(notices), err => {
        if (err) {
            res.send("false");
        }
    });
    res.send("true");
});

app.get("/delete", function(req, res) {
    let id = req.query["id"];
    notices.splice(id, 1);
    fs.writeFile("./data/notices.json", JSON.stringify(notices), err => {
        if (err) {
            res.send("false");
        }
    });
    res.send("true");
});

app.listen(PORT, function (req, res) {
    console.log("Listening on port 3000...");
});