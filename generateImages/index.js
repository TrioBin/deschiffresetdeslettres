const { createCanvas } = require("canvas");
const fs = require("fs");

// Dimensions for the image
const width = 100;
const height = 150;
const textHeight = Math.min(0.75 * height, 0.75 * width);

// Instantiate the canvas object
const canvas = createCanvas(width, height);
const context = canvas.getContext("2d");

const list = [
    "A",
    "B",
    "C",
    "D",
    "E",
    "F",
    "G",
    "H",
    "I",
    "J",
    "K",
    "L",
    "M",
    "N",
    "O",
    "P",
    "Q",
    "R",
    "S",
    "T",
    "U",
    "V",
    "W",
    "X",
    "Y",
    "Z",
    "1",
    "2",
    "3",
    "4",
    "5",
    "6",
    "7",
    "8",
    "9",
    "0"
]

complexletter = [
    {
        "name": "moins",
        "value": "-"
    },
    {
        "name": "plus",
        "value": "+"
    },
    {
        "name": "div",
        "value": "/"
    },
    {
        "name": "mult",
        "value": "*"
    }
]

list.forEach(lettre => {
    context.fillStyle = "#000fff";
    context.fillRect(0, 0, width, height);

    context.font = `bold ${textHeight}px 'Arial'`;
    context.textAlign = "center";
    context.fillStyle = "#fff";

    context.fillText(lettre, width / 2, height / 2 + textHeight / 3);

    // Write the image to file
    const buffer = canvas.toBuffer("image/png");
    fs.writeFileSync("../src/main/resources/images/chiffrescard/" + lettre + ".png", buffer);
});

complexletter.forEach(lettre => {
    context.fillStyle = "#000fff";
    context.fillRect(0, 0, width, height);

    context.font = `bold ${textHeight}px 'Arial'`;
    context.textAlign = "center";
    context.fillStyle = "#fff";

    context.fillText(lettre.value, width / 2, height / 2 + textHeight / 3);

    // Write the image to file
    const buffer = canvas.toBuffer("image/png");
    fs.writeFileSync("../src/main/resources/images/chiffrescard/" + lettre.name + ".png", buffer);
});

const unicodelist = [
    " ",
    "!",
    "\"",
    "#",
    "$",
    "%",
    "&",
    "'",
    "(",
    ")",
    "*",
    "+",
    ",",
    "-",
    ".",
    "/",
    "0",
    "1",
    "2",
    "3",
    "4",
    "5",
    "6",
    "7",
    "8",
    "9",
    ":",
    ";",
    "<",
    "=",
    ">",
    "?",
    "@",
    "A",
    "B",
    "C",
    "D",
    "E",
    "F",
    "G",
    "H",
    "I",
    "J",
    "K",
    "L",
    "M",
    "N",
    "O",
    "P",
    "Q",
    "R",
    "S",
    "T",
    "U",
    "V",
    "W",
    "X",
    "Y",
    "Z",
    "[",
    "\\",
    "]",
    "^",
    "_",
    "`",
    "a",
    "b",
    "c",
    "d",
    "e",
    "f",
    "g",
    "h",
    "i",
    "j",
    "k",
    "l",
    "m",
    "n",
    "o",
    "p",
    "q",
    "r",
    "s",
    "t",
    "u",
    "v",
    "w",
    "x",
    "y",
    "z",
    "{",
    "|",
    "}",
    "~"
]

heightList = [
    10,
    20,
    30,
    40,
    50,
    60,
    70,
    80,
    90,
    100
]

fontTypeList = [
    "bold",
    "normal",
    "italic"
]

fontNameList = [
    "Arial",
    "Courier New",
    "Georgia",
    "Times New Roman",
    "Verdana"
]

fontNameList.forEach(name => {
    fontTypeList.forEach(type => {
        heightList.forEach(height => {
            context.font = `${type} ${height}px '${name}'`;
            var unicanvas = createCanvas(context.measureText(unicodelist.join("")).width + unicodelist.length * 2, height * 1.25);
            var unicontext = unicanvas.getContext("2d");
            unicontext.fillStyle = "#ff00ff";
            unicontext.fillRect(0, 0, unicanvas.width, unicanvas.height);
            unicontext.font = `${type} ${height}px '${name}'`;
            xoffset = 0;
            unicodelist.forEach((lettre, index) => {
                unicontext.fillStyle = "#fff";
                unicontext.fillText(lettre, xoffset, height);
                unicontext.fillStyle = "#00f";
                unicontext.fillRect(xoffset, 0, 1, 1);
                xoffset += Math.ceil(unicontext.measureText(lettre).width) + 1;
                unicontext.fillStyle = "#ff0";
                unicontext.fillRect(xoffset - 1, 0, 1, 1);
            });
            const buffer = unicanvas.toBuffer("image/png");
            fs.writeFileSync(`../src/main/resources/fonts/${name}_${type}_${height}.png`, buffer);
        });
    });
});