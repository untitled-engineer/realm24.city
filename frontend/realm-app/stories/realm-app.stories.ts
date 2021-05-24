import { html, TemplateResult } from 'lit-html';
import '../src/realm-app.js';

export default {
  title: 'RealmApp',
  component: 'realm-app',
  argTypes: {
    backgroundColor: { control: 'color' },
  },
};

interface Story<T> {
  (args: T): TemplateResult;
  args?: Partial<T>;
  argTypes?: Record<string, unknown>;
}

interface ArgTypes {
  title?: string;
  backgroundColor?: string;
}

const Template: Story<ArgTypes> = ({
  title,
  backgroundColor = 'white',
}: ArgTypes) => html`
  <realm-app
    style="--realm-app-background-color: ${backgroundColor}"
    .title=${title}
  ></realm-app>
`;

export const App = Template.bind({});
App.args = {
  title: 'My app',
};
