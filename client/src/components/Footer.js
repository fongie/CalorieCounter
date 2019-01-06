import React from 'react';

/**
 * The global Footer for the application.
 */
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
