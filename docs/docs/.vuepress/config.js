module.exports = {
    base: '/',
    head: [['link', { rel: 'icon', href: '/images/favicon.png' }]],
    title: 'PhoneAccount Abuse Detector',
    description: "Simple application to enumerate and detect any application that (ab)uses adding an indefinite amount of PhoneAccount(s) to Android's Telecom Manager",
    themeConfig: {
        logo: '/images/logo_transparent.png',
        darkMode: false,
        navbar: [
            {
                text: 'Guide',
                link: 'guide',
            },
        ],
        repo: 'linuxct/PhoneAccountDetector'
    },
    plugins: [
        '@vuepress/back-to-top'
    ]
}