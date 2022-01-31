(function() {
    const getCryptoPrice = async (crypto, ip = null) => {
        let searchParams = '';
        if (ip) {
            const queryParams = { ipAddress: ip };
            const urlSearchParams = new URLSearchParams(queryParams);
            searchParams = `?${urlSearchParams.toString()}`;
        }
        const url = `/crypto/exchange/rate/${crypto}${searchParams}`;
        let response, payload;
        try {
            response = await fetch(url);
            payload = await response.json();
        } catch (err) {
            throw new Error("Failed to send a request, please check your internet connection");
        }

        if (response.status !== 200) {
            throw new Error(payload.message);
        }

        return payload;
    }

    const setMessage = (message, hasError = false) => {
        const messageContainer = document.querySelector('.message-container');
        if (hasError) {
            messageContainer.classList.add('message-error');
        } else {
            messageContainer.classList.remove('message-error');
        }
        messageContainer.innerHTML = message;
    }

    const handleCryptoSubmit = async (event) => {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        const crypto = formData.get("crypto_currency");
        const ip = formData.get("ip_override").trim();

        try {
            const pricing = await getCryptoPrice(crypto, ip);
            const message = `Unit price is ${pricing.currencySymbol} : ${pricing.rate}`;
            setMessage(message);
        } catch (err) {
            setMessage(err.message, true);
        }
    }

    const registerCryptoSubmitHandler = () => {
        const form = document.querySelector(".frm-crypto");
        form.addEventListener('submit', handleCryptoSubmit);
    }

    document.addEventListener("DOMContentLoaded", function(event) {
        registerCryptoSubmitHandler();
    });
})();