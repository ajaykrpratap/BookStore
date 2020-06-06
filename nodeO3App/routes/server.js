var express = require('express');
var router = express.Router();

const books = [
    { title: 'Harry Potter', id: 1 },
    { title: 'Twilight', id: 2 },
    { title: 'Lorien Legacies', id: 3 }
];
const superviseeList = [{
    date: "06/10/2020",
    meetingDone: true,
    meetingDate: "06/10/2020",
    comments: "Testing",
    status: "Green",
},
{
    date: "06/16/2020",
    meetingDone: true,
    meetingDate: "06/16/2020",
    comments: "Testing by Ajay",
    status: "Green",
}];
const supervisee = ["Puneet", "Dipti", "Ajay"]
router.get('/supervisee', (req, res) => {
    res.send(supervisee);
});

router.get('/o3SuperviseeDetails', (req, res) => {
    res.send(superviseeList);
});

module.exports = router;


