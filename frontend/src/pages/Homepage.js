import '../styles/pages/HomePage.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-icons/font/bootstrap-icons.css'
import ActionButton from '../components/ActionButton'
import BankAccountCard from '../components/BankAccountCard'
import CardDisplay from '../components/CardDisplay'
import Navbar from '../components/Navbar'
import TransactionHistory from '../components/TransactionHistory'
import { useState } from 'react';
import { NavLink } from 'react-router-dom'
import CardScroll from '../components/CardScroll'

const HomePage = () => {
  const bankAccountNumber = "1234 5678 9012 3456";

  const creditCards = ["Card 1", "Card 2", "Card 3"]; // Replace with your credit card data

  // Dropdown
  const initialSelectedCard = "1234 5678 9012 3456";

  const [selectedCard, setSelectedCard] = useState(initialSelectedCard);

  const cardList = [
    {
      cardNumber: "1234 5678 9012 3456",
      cardHolder: "John Doe",
      expiryDate: "12/23",
      cardType: "Visa",
    },
    {
      cardNumber: "9876 5432 1098 7654",
      cardHolder: "Jane Doe",
      expiryDate: "11/22",
      cardType: "MasterCard",
    },
    // Add more cards as needed
  ];

  const handleCardSelect = (cardNumber) => {
    setSelectedCard(cardNumber);
  };

  return (
    <div className="HomePage">
      <div className="left-column">
        <Navbar />
      </div>

    return (
        <div className="HomePage">
            <div className='left-column'>
                <Navbar />
            </div>

      <div className="right-column">
        <div className="top">
          <h1>Bank Account</h1>
        </div>

                <div className='middle'>
                    <div className='card-display'>
                        <CardScroll />
                    </div>
                    <div className='button-list'>
                        <NavLink to="/credit-cards">
                            <ActionButton>
                                <i className='bi bi-credit-card' />
                                <span className='ms-2'>Cards</span>
                            </ActionButton>
                        </NavLink>
                        <ActionButton>
                            <i className='bi bi-gift' />
                            <span className='ms-2'>Cashback</span>
                        </ActionButton>
                    </div>
                </div>
                <hr />
                <div className='bottom'>
                    <div className='bottom-left'>
                        <div className='bottom-left-1'>
                            <BankAccountCard bankName="SG Bank" accountNumber="123456789" initialBalance="5000" />
                            <BankAccountCard bankName="HK Bank" accountNumber="999999999" initialBalance="15000" />
                        </div>
                        <div className='bottom-left-2'>
                            <h2>Transaction History</h2>
                            <TransactionHistory />
                        </div>
                    </div>

          <div className="bottom-right">
            <div className="button-list">
              <NavLink to="/credit-cards">
                <ActionButton>
                  <i className="bi bi-plus-circle" />
                  <span className="ms-2">Deposit</span>
                </ActionButton>
              </NavLink>
              <NavLink to="/credit-cards">
                <ActionButton>
                  <i className="bi bi-dash-circle" />
                  <span className="ms-2">Withdraw</span>
                </ActionButton>
              </NavLink>
              <NavLink to="/credit-cards">
                <ActionButton>
                  <i className="bi bi-arrow-right-circle" />
                  <span className="ms-2">Transfer</span>
                </ActionButton>
              </NavLink>
              <NavLink to="/credit-cards">
                <ActionButton>
                  <i className="bi bi-receipt" />
                  <span className="ms-2">Payment</span>
                </ActionButton>
              </NavLink>
            </div>
          </div>
        </div>

        <div>
          {/* // in here we pass endpoint bla bla bla */}
          {transactionData.map((transactionItem) => (
            <TransactionCard transactionData={transactionItem} />
          ))}
        </div>

        <div className="middle">
          <div className="card-display">
            <CardDisplay />
          </div>
          <div className="button-list">
            <NavLink to="/credit-cards">
              <ActionButton>
                <i className="bi bi-credit-card" />
                <span className="ms-2">Cards</span>
              </ActionButton>
            </NavLink>
            <ActionButton>
              <i className="bi bi-gift" />
              <span className="ms-2">Cashback</span>
            </ActionButton>
          </div>
        </div>
        <hr />
        <div className="bottom">
          <div className="bottom-left">
            <div className="bottom-left-1">
              <BankAccountCard
                bankName="SG Bank"
                accountNumber="123456789"
                initialBalance="5000"
              />
              <BankAccountCard
                bankName="HK Bank"
                accountNumber="999999999"
                initialBalance="15000"
              />
            </div>
            <div className="bottom-left-2">
              <h2>Transaction History</h2>
              <TransactionHistory />
            </div>
          </div>

          <div className="bottom-right">
            <div className="button-list">
              <NavLink to="/credit-cards">
                <ActionButton>
                  <i className="bi bi-credit-card" />
                  <span className="ms-2">Cards</span>
                </ActionButton>
              </NavLink>
              <ActionButton>
                <i className="bi bi-gift" />
                <span className="ms-2">Cashback</span>
              </ActionButton>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;





// userTable
// we will have all credit cards and bank account
// we  will show each card
// when we pick, we change to the currentCard State
// we can show data for current card