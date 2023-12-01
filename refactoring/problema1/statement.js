const plays = require("./data/plays.json");
const invoices = require("./data/invoices.json");

function calculateAmount(play, audience) {
  let amount = 0;
  switch (play.type) {
    case "tragedy":
      amount = 40000;
      if (audience > 30) {
        amount += 1000 * (audience - 30);
      }
      break;
    case "comedy":
      amount = 30000;
      if (audience > 20) {
        amount += 10000 + 500 * (audience - 20);
      }
      amount += 300 * audience;
      break;
    default:
      throw new Error(`Unknown play type: ${play.type}`);
  }
  return amount;
}

function calculateVolumeCredits(perf, play) {
  let credits = Math.max(perf.audience - 30, 0);
  if (play.type === "comedy") credits += Math.floor(perf.audience / 5);
  return credits;
}

function statement(invoice, plays) {
  let totalAmount = 0;
  let volumeCredits = 0;
  const format = new Intl.NumberFormat("en-US", {
    style: "currency", currency: "USD", minimumFractionDigits: 2
  }).format;

  let result = `Statement for ${invoice.customer}\n`;

  for (let perf of invoice.performances) {
    const play = plays[perf.playID];
    const thisAmount = calculateAmount(play, perf.audience);

    volumeCredits += calculateVolumeCredits(perf, play);

    result += `  ${play.name}: ${format(thisAmount / 100)} (${perf.audience} seats)\n`;
    totalAmount += thisAmount;
  }

  result += `Amount owed is ${format(totalAmount / 100)}\n`;
  result += `You earned ${volumeCredits} credits\n`;
  return result;
}

console.log(statement(invoices[0], plays));
