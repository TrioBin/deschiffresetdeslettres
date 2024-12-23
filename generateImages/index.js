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
    "0",
    "10",
    "25",
    "50",
    "75",
    "100",
    "+",
    "-",
    "*",
    "/",
]

list.forEach(lettre => {
    context.fillStyle = "#000fff";
    context.fillRect(0, 0, width, height);

    if (lettre == "100") {
        context.font = `bold ${textHeight * 0.75}px 'Arial'`;
        context.textAlign = "center";
        context.fillStyle = "#fff";

        context.fillText(lettre, width / 2, height / 2 + textHeight * 0.75 / 3);
    } else {
        context.font = `bold ${textHeight}px 'Arial'`;
        context.textAlign = "center";
        context.fillStyle = "#fff";

        context.fillText(lettre, width / 2, height / 2 + textHeight / 3);
    }

    // Write the image to file
    const buffer = canvas.toBuffer("image/png");
    fs.writeFileSync("../src/main/resources/images/chiffrescard/" + lettre + ".png", buffer);
});