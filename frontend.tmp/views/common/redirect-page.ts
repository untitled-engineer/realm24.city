import {customElement, html, property} from 'lit-element';
import {View} from '../../views/view';

@customElement('redirect-page')
export class RedirectPage extends View {

  @property()
  private seconds = 0;

  constructor() {
    super()
    this.seconds = 0;
    let seconds = 0;
    const interval = setInterval(function ()  {
      console.log('hello')
      seconds++;
      if (seconds == 5) {
        clearInterval(interval);

        window.location.reload();
      }
    }, 1000);
  }

  render() {
    return html`
      <div>You are ben redirected for authorisation in ${this.seconds}</div>
    `;
  }
}
