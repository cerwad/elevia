/**
 * Utility file for the page Partie
 */

const smileIco = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="green"' +
    'class="bi bi-emoji-smile-fill" viewBox="0 0 16 16">' +
    '<path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zM7 6.5C7 7.328 6.552 8 6 8s-1-.672-1-1.5S5.448 5 6 5s1 .672 1 1.5zM4.285 9.567a.5.5 0 0 1 .683.183A3.498 3.498 0 0 0 8 11.5a3.498 3.498 0 0 0 3.032-1.75.5.5 0 1 1 .866.5A4.498 4.498 0 0 1 8 12.5a4.498 4.498 0 0 1-3.898-2.25.5.5 0 0 1 .183-.683zM10 8c-.552 0-1-.672-1-1.5S9.448 5 10 5s1 .672 1 1.5S10.552 8 10 8z" /></svg>';

const badIco = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="red"' +
    'class="bi bi-emoji-frown-fill" viewBox="0 0 16 16">' +
    '<path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zM7 6.5C7 7.328 6.552 8 6 8s-1-.672-1-1.5S5.448 5 6 5s1 .672 1 1.5zm-2.715 5.933a.5.5 0 0 1-.183-.683A4.498 4.498 0 0 1 8 9.5a4.5 4.5 0 0 1 3.898 2.25.5.5 0 0 1-.866.5A3.498 3.498 0 0 0 8 10.5a3.498 3.498 0 0 0-3.032 1.75.5.5 0 0 1-.683.183zM10 8c-.552 0-1-.672-1-1.5S9.448 5 10 5s1 .672 1 1.5S10.552 8 10 8z" /></svg>';

const row = '<div class="row gx-1">';
const endrow = '</div>';

const col = '<div class="col text-nowrap">';
const endcol = '</div>';
/**
 * Operation class representing a simple caclulation like 3x3
 */
class Operation {

    /**
     * 
     * @param {Left Operand} lOperand 
     * @param {Right Number} rOperand
     * @param {Operator} operator 
     * @param {Result} result
     */
    constructor(lOperand, rOperand, operator) {
        this.lOperand = lOperand;
        this.rOperand = rOperand;
        this.operator = operator;
        this.result = 0;
    }


    calculate() {
        switch (this.operator) {
            case 'x':
                return this.lOperand * this.rOperand;
            case '+':
                return this.lOperand + this.rOperand;
            case '-':
                return this.lOperand - this.rOperand;
            case '/':
                return this.lOperand / this.rOperand;
        }
    }

    validResult() {
        if (isNaN(this.result)) {
            throw 'Result is not a number!';
        }
        return this.calculate() == this.result;
    }

    toString() {
        return ' ' + this.lOperand + this.operator + this.rOperand + '=';
    }
}

function onClick(operations) {
    if (!timerStarted) {
        timer.start({ precision: 'secondTenths' });
        timer.addEventListener('secondTenthsUpdated', function (e) {
            $('#secondTenths .values').html(timer.getTimeValues().toString(['hours', 'minutes', 'seconds', 'secondTenths']));
        });
        timerStarted = true;
    }

    $('#resultList').html(genHtml(operations));
    operation = getRandomMultiplication();
    operations.push(operation);
    $('#operation').text(operation.toString())
    $('#result').val("");
    if(operations.length >= 10){
        window.location.href = "partsuccess.html";
    }
}

function genHtml(operations) {
    var listHtml = '';
    console.log('list ope ' + operations);
    operations.forEach(function (item, index, array) {
        var icon = smileIco;
        if (!item.validResult(item)) {
            icon = badIco;
        }
        if (index % 10 == 0) {
            if (index > 0) {
                listHtml += endrow;
            }
            listHtml += row;
        }
        listHtml += col + icon + item.toString() + item.result + endcol;
        
    });
    return listHtml;
}

function getRandomMultiplication(){
    return new Operation(getRandomInt(), getRandomInt(), 'x');
}

function getRandomInt() {
    var n = Math.floor(Math.random() * 10);
    if(n < 2) {
        n = 5;
    }
    return n;
  }