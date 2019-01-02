import React from 'react';

const Footer = () => {
    return (
        <div>
            <CopyrightLink />
        </div>
    );
}

const CopyrightLink = () => {
    return (
        <p>
            &copy;
            <a
                style={{textDecoration: 'none'}}
                href="http://maxk.se"
            >
                2018 Max Körlinge
            </a>
        </p>
    );
}

export default Footer;
