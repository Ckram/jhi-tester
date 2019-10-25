export interface IPotato {
  id?: number;
  shape?: string;
  size?: string;
}

export class Potato implements IPotato {
  constructor(public id?: number, public shape?: string, public size?: string) {}
}
