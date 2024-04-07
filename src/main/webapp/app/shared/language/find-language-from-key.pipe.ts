import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  standalone: true,
  name: 'findLanguageFromKey',
})
export default class FindLanguageFromKeyPipe implements PipeTransform {
  private languages: { [key: string]: { name: string; rtl?: boolean } } = {
    en: { name: 'English' },
    'ar-ly': { name: 'العربية', rtl: true },
    'az-Latn-az': { name: 'Azərbaycan dili' },
    bg: { name: 'Български' },
    cs: { name: 'Český' },
    da: { name: 'Dansk' },
    nl: { name: 'Nederlands' },
    de: { name: 'Deutsch' },
    el: { name: 'Ελληνικά' },
    hu: { name: 'Magyar' },
    it: { name: 'Italiano' },
    pl: { name: 'Polski' },
    'pt-br': { name: 'Português (Brasil)' },
    ro: { name: 'Română' },
    ru: { name: 'Русский' },
    sr: { name: 'Srpski' },
    es: { name: 'Español' },
    sv: { name: 'Svenska' },
    tr: { name: 'Türkçe' },
    // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
  };

  transform(lang: string): string {
    return this.languages[lang].name;
  }

  isRTL(lang: string): boolean {
    return Boolean(this.languages[lang].rtl);
  }
}
