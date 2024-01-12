import '../styles/pages/TransferPage.css'
import Navbar from '../components/Navbar'
import React, { useState, useEffect } from 'react'
import { left } from '@popperjs/core'

const TransferPage = () => {
  const [fromAccount, setFromAccount] = useState('123456789');
  const [toAccount, setToAccount] = useState('');
  const [amount, setAmount] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleToAccountChange = (event) => {
    const input = event.target.value;
    
    if (/^\d*$/.test(input)) {
      setToAccount(input);
    }
  };

  const handleAmountChange = (event) => {
    const input = event.target.value;
    
    if (/^\d*\.?\d*$/.test(input)) {
      setAmount(input);
    }
  };

  const handlePayButtonClick = (event) => {
    event.preventDefault();
    
    if (!toAccount && !amount) {
      setErrorMessage('To and Amount fields are required');
      setSuccessMessage('');
    } else if (!toAccount) {
      setErrorMessage('To field is required');
      setSuccessMessage('');
    } else if (!amount) {
      setErrorMessage('Amount field is required');
      setSuccessMessage('');
    } else {
      // Implement payment logic
      // API call for backend validation
      console.log(`Payment initiated from ${fromAccount} to ${toAccount} with amount ${amount}`);
      setErrorMessage('');
      setSuccessMessage('Successfully transferred');
      
      setToAccount('');
      setAmount('');
    }
  };

  useEffect(() => {
    // Fetch bank account number from endpoint
    // Update the state
    {/*
    fetch('your_endpoint_to_get_bank_account')
      .then((response) => response.json())
      .then((data) => {
        if (data && data.bankAccount) {
          setFromAccount(data.bankAccount);
        }
      })
      .catch((error) => {
        console.error('Error fetching bank account:', error);
      });
    */}
  }, []);

  return (
    <div className="HomePage">
        <div className='left-column'>
            <Navbar />
        </div>

        <div className='right-column'>
            <div className='top'>
                <h1>Transfer</h1>
            </div>

            <div className="transfer-card-body">
                <form style={{width:'50%', marginLeft:'325px'}}>
                    <div className="form-group">
                        <label htmlFor="fromAccount" className="labelAmount mt-2">From:</label>
                        <input type="text" id="fromAccount" value={fromAccount} className="form-control mt-2" readOnly />
                    </div>
                    <div className="form-group">
                        <label htmlFor="toAccount" className="labelAmount mt-2">To:</label>
                        <input type="text" id="toAccount" value={toAccount} onChange={handleToAccountChange} className="form-control mt-2" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="amount" className="labelAmount mt-2">Amount:</label>
                        <input type="text" id="amount" value={amount} onChange={handleAmountChange} className="form-control mt-2" />
                    </div>

                    <div className="btn-group mt-3">
                      <button onClick={handlePayButtonClick} className="btn btn-primary">
                          Transfer
                      </button>
                    </div>
                    {errorMessage && <div className="error-message">{errorMessage}</div>}
                    {successMessage && <div className="success-message">{successMessage}</div>}
                </form>
            </div>
        </div>
    </div>
  );
};

export default TransferPage;
