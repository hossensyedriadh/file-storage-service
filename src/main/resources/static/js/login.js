function handlePasswordVisibilityToggle() {
    let password_field = document.getElementById('login_passphrase');
    let toggle_switch = document.getElementById('password_toggle');

    if (password_field.type === 'password') {
        password_field.type = 'text';
        toggle_switch.className = 'eye slash link icon';
    } else {
        password_field.type = 'password';
        toggle_switch.className = 'eye link icon';
    }
}
