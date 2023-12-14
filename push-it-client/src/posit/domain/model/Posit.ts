export class Posit {
    uuid: string;
    title: string;
    content: string;
    authorUuid: string;

    constructor(title: string, content: string, authorUuid: string, uuid?: string) {
        this.uuid = uuid ?? '';
        this.title = title;
        this.content = content;
        this.authorUuid = authorUuid;
    }
}